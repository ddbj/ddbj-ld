package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.primary.common.SuppressedMetadataDao;
import com.ddbj.ld.app.transact.dao.primary.sra.DRAAccessionDao;
import com.ddbj.ld.app.transact.dao.primary.sra.DRALiveListDao;
import com.ddbj.ld.app.transact.dao.primary.sra.SRARunDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.run.RUNClass;
import com.ddbj.ld.data.beans.sra.run.RunConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SRARunService {

    private final JsonModule jsonModule;
    private final MessageModule messageModule;
    private final SearchModule searchModule;

    private final SRARunDao runDao;
    private final SuppressedMetadataDao suppressedMetadataDao;
    private final DRALiveListDao draLiveListDao;
    private final DRAAccessionDao draAccessionDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<UpdateRequest> get(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var requests = new ArrayList<UpdateRequest>();

            var isStarted = false;

            // 固定値
            var startTag  = XmlTagEnum.SRA_RUN.start;
            var endTag    = XmlTagEnum.SRA_RUN.end;

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入る可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var json = this.jsonModule.xmlToJson(sb.toString());
                    var bean = this.getBean(json, path);
                    var updateRequest = this.jsonModule.getUpdateRequest(bean);

                    if(null == bean) {
                        // errorInfoへの格納は上述のgetBeanから呼び出されるgetProperties内で行っているため、行わない

                        log.warn("Converting json to bean.:{}", json);

                        continue;
                    }

                    var identifier = bean.getIdentifier();

                    if(null == identifier || identifier.isBlank()) {
                        log.warn("Metadata doesn't have identifier.:{}", json);

                        List<String> values;
                        var key = "Metadata doesn't have identifier";

                        if(null == (values = this.errorInfo.get(key))) {
                            values = new ArrayList<>();
                        }

                        values.add(json);

                        this.errorInfo.put(key, values);

                        continue;
                    }

                    if(null == updateRequest) {
                        log.warn("Converting json to update requests.:{}", json);

                        continue;
                    }

                    requests.add(updateRequest);
                }
            }

            return requests;

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public List<DeleteRequest> getDeleteRequests(final String date) {
        var type = TypeEnum.RUN.type;

        // suppressedからpublic, unpublishedになったデータはsuppressedテーブルから削除する
        var suppressedToPublicRecords = this.runDao.selSuppressedToPublic(date);
        var suppressedToUnpublishedRecords = this.runDao.selSuppressedToUnpublished(date);
        var suppressedArgs = new ArrayList<Object[]>();

        for (var record : suppressedToPublicRecords) {
            suppressedArgs.add(new Object[] {
                    record.getAccession()
            });
        }

        for (var record : suppressedToUnpublishedRecords) {
            suppressedArgs.add(new Object[] {
                    record.getAccession()
            });
        }

        this.suppressedMetadataDao.bulkDelete(suppressedArgs);

        var toSuppressedRecords = this.runDao.selToSuppressed(date);
        var toUnpublishedRecords = this.runDao.selToUnpublished(date);

        var deleteRequests = new ArrayList<DeleteRequest>();
        var getRequests = new MultiGetRequest();

        for(var record : toSuppressedRecords) {
            var identifier = record.getAccession();
            deleteRequests.add(new DeleteRequest(type).id(identifier));

            getRequests.add(new MultiGetRequest.Item(type, identifier));
        }

        for(var record : toUnpublishedRecords) {
            var identifier = record.getAccession();
            deleteRequests.add(new DeleteRequest(type).id(identifier));
        }

        // suppressedとなったレコードをSuppressedテーブルに登録
        if(getRequests.getItems().size() > 0) {
            var getResponses = this.searchModule.get(getRequests);
            var recordList = new ArrayList<Object[]>();

            for(var response: getResponses) {
                var res = response.getResponse();

                if(null == res) {
                    continue;
                }

                var source = res.getSourceAsString();
                var json = this.jsonModule.paintPretty(source);

                if(null == source) {
                    log.error("Getting data from elasticsearch is failed: {}", response.getId());

                    continue;
                }

                recordList.add(new Object[] {
                        response.getId(),
                        type,
                        json
                });
            }

            this.suppressedMetadataDao.bulkInsert(recordList);
        }

        return deleteRequests;
    }

    public void validate(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_RUN.start;
            var endTag    = XmlTagEnum.SRA_RUN.end;

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                if(line.contains(endTag)) {
                    var json = this.jsonModule.xmlToJson(sb.toString());
                    this.getProperties(json, path);
                }
            }

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public ArrayList<AccessionsBean> getDraAccessionList(
            final String path,
            final String submissionId
            ) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_RUN.start;
            var endTag    = XmlTagEnum.SRA_RUN.end;

            var accessionList = new ArrayList<AccessionsBean>();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                if(line.contains(endTag)) {
                    var json = this.jsonModule.xmlToJson(sb.toString());
                    var properties = this.getProperties(json, path);

                    if(null == properties) {
                        continue;
                    }

                    var accession = properties.getAccession();
                    var experimentRef = properties.getExperimentRef();
                    var experimentId = null == experimentRef ? null : experimentRef.getAccession();

                    var liveList = this.draLiveListDao.select(accession, submissionId);

                    if(null == liveList) {
                        log.warn("Can't get livelist: {}", accession);

                        continue;
                    }

                    var bean = new AccessionsBean(
                            accession,
                            liveList.getSubmission(),
                            StatusEnum.PUBLIC.status,
                            liveList.getUpdated(),
                            liveList.getUpdated(),
                            liveList.getUpdated(),
                            liveList.getType(),
                            liveList.getCenter(),
                            "public".equals(liveList.getVisibility()) ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : VisibilityEnum.CONTROLLED_ACCESS.visibility,
                            liveList.getAlias(),
                            experimentId,
                            null,
                            null,
                            (byte) 1,
                            null,
                            null,
                            liveList.getMd5sum(),
                            null,
                            null,
                            null,
                            null,
                            null
                    );

                    accessionList.add(bean);
                }
            }

            return accessionList;

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void noticeErrorInfo() {
        if(this.errorInfo.size() > 0) {
            this.messageModule.noticeErrorInfo(TypeEnum.RUN.type, this.errorInfo);

        }

        this.errorInfo = new HashMap<>();
    }

    public void rename(final String date) {
        this.runDao.drop();
        this.runDao.rename(date);
        this.runDao.renameIndex(date);
    }

    private RUNClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = RunConverter.fromJsonString(json);

            return bean.getRun();
        } catch (IOException e) {
            log.error("Converting metadata to bean is failed. xml path: {}, json:{}", path, json, e);

            var message = e.getLocalizedMessage()
                    .replaceAll("\n at.*.", "")
                    .replaceAll("\\(.*.", "");

            List<String> values;

            if(null == (values = this.errorInfo.get(message))) {
                values = new ArrayList<>();
            }

            values.add(json);

            this.errorInfo.put(message, values);

            return null;
        }
    }

    private JsonBean getBean(
            final String json,
            final String path
    ) {
        // 固定値
        // typeの設定
        var type = TypeEnum.RUN.getType();
        // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
        List<SameAsBean> sameAs = null;
        // "DRA"固定
        var isPartOf = IsPartOfEnum.SRA.getIsPartOf();
        // 生物名とIDはSampleのみの情報であるため空情報を設定
        OrganismBean organism = null;

        // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
        var bioProjectType = TypeEnum.BIOPROJECT.type;
        var bioSampleType = TypeEnum.BIOSAMPLE.type;
        var submissionType = TypeEnum.SUBMISSION.type;
        var experimentType = TypeEnum.EXPERIMENT.type;
        var studyType = TypeEnum.STUDY.type;
        var sampleType = TypeEnum.SAMPLE.type;

        // Json文字列を項目取得用、バリデーション用にBean化する
        // Beanにない項目がある場合はエラーを出力する
        var properties = this.getProperties(json, path);

        if(null == properties) {
            return null;
        }

        // accesion取得
        var identifier = properties.getAccession();

        // Title取得
        var title = properties.getTitle();

        // Description に該当するデータは存在しないためrunではnullを設定
        String description = null;

        // name 取得
        var name = properties.getAlias();

        // sra-run/[DES]RA??????
        var url = this.jsonModule.getUrl(type, identifier);

        var search = this.jsonModule.beanToJson(properties);

        var distribution = this.jsonModule.getDistribution(type, identifier);

        var dbXrefs = new ArrayList<DBXrefsBean>();

        // runはanalysis以外一括で取得できる
        // bioproject、biosample、submission、experiment、study、sample、status、visibility、date_created、date_modified、date_published
        AccessionsBean run;

        if(identifier.startsWith("DR")) {
            run = this.draAccessionDao.select(identifier);
        } else {
            run = this.runDao.select(identifier);
        }

        var bioProjectId = null == run ? null : run.getBioProject();
        var bioSampleId = null == run ? null : run.getBioSample();
        var submissionId = null == run ? null : run.getSubmission();
        var experimentId = null == run ? null : run.getExperiment();
        var studyId = null == run ? null : run.getStudy();
        var sampleId = null == run ? null : run.getSample();

        if(null != bioProjectId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(bioProjectId, bioProjectType));
        }

        if(null != bioSampleId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(bioSampleId, bioSampleType));
        }

        if(null != submissionId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(submissionId, submissionType));
        }

        if(null != experimentId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(experimentId, experimentType));
        }

        if(null != studyId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(studyId, studyType));
        }

        if(null != sampleId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(sampleId, sampleType));
        }

        List<DownloadUrlBean> downloadUrl = null;

        if(null != submissionId && null != experimentId) {
            // ファイル名を作る
            var sraFileName = identifier + ".sra";
            var fastqFileName = identifier + "'s fastq";

            var submissionPrefix = submissionId.substring(0, 6);
            var experimentPrefix = experimentId.substring(0, 6);

            // 根本のURLを作る
            var httpsSraRoot = "https://ddbj.nig.ac.jp/public/ddbj_database/dra/sralite/ByExp/litesra/";
            var ftpSraRoot = "ftp://ftp.ddbj.nig.ac.jp/ddbj_database/dra/sralite/ByExp/litesra/";
            var httpsSraUrl = "";
            var ftpSraUrl = "";

            var httpsFastqUrl = "https://ddbj.nig.ac.jp/public/ddbj_database/dra/fastq/" + submissionPrefix + "/" + submissionId + "/" + experimentId;
            var ftpFastqUrl = "ftp://ftp.ddbj.nig.ac.jp/ddbj_database/dra/fastq/" + submissionPrefix + "/" + submissionId + "/" + experimentId;

            if(identifier.startsWith("SRR")) {
                httpsSraUrl = httpsSraRoot + "SRX/" + experimentPrefix + "/" + experimentId + "/" + identifier + "/" + sraFileName;
                ftpSraUrl   = ftpSraRoot  + "SRX/" + experimentPrefix + "/" + experimentId + "/" + identifier + "/" + sraFileName;
            } else if(identifier.startsWith("ERR")) {
                httpsSraUrl = httpsSraRoot + "ERX/" + experimentPrefix + "/" + experimentId + "/" + identifier + "/" + sraFileName;
                ftpSraUrl   = ftpSraRoot  + "ERX/" + experimentPrefix + "/" + experimentId + "/" + identifier + "/" + sraFileName;
            } else if(identifier.startsWith("DRR")) {
                httpsSraUrl = "https://ddbj.nig.ac.jp/public/ddbj_database/dra/sra/ByExp/sra/DRX/" + experimentPrefix + "/" + experimentId + "/" + identifier + "/" + sraFileName;
                ftpSraUrl   = "ftp://ftp.ddbj.nig.ac.jp/ddbj_database/dra/sra/ByExp/sra/DRX/" + experimentPrefix + "/" + experimentId + "/" + identifier + "/"  + sraFileName;
            }

            downloadUrl = new ArrayList<>();

            downloadUrl.add(new DownloadUrlBean(
                    "sra",
                    sraFileName,
                    httpsSraUrl,
                    ftpSraUrl
            ));

            downloadUrl.add(new DownloadUrlBean(
                    "fastq",
                    fastqFileName,
                    httpsFastqUrl,
                    ftpFastqUrl
            ));
        }

        var dbXrefsStatistics = new ArrayList<DBXrefsStatisticsBean>();
        var statisticsMap = new HashMap<String, Integer>();

        for(var dbXref : dbXrefs) {
            var dbXrefType = dbXref.getType();
            var count = null == statisticsMap.get(dbXrefType) ? 1 : statisticsMap.get(dbXrefType) + 1;

            statisticsMap.put(dbXrefType, count);
        }

        for (var entry : statisticsMap.entrySet()) {
            dbXrefsStatistics.add(new DBXrefsStatisticsBean(
                    entry.getKey(),
                    entry.getValue()
            ));
        }

        // status, visibility、日付取得処理
        var status = null == run || null == run.getStatus() ? StatusEnum.PUBLIC.status : run.getStatus();
        var visibility =null == run || null == run.getVisibility() ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : run.getVisibility();
        var dateCreated = this.jsonModule.parseLocalDateTime(null == run ? null : run.getReceived());
        var dateModified = this.jsonModule.parseLocalDateTime(null == run ? null : run.getUpdated());
        var datePublished = this.jsonModule.parseLocalDateTime(null == run ? null : run.getPublished());

        return new JsonBean(
                identifier,
                title,
                description,
                name,
                type,
                url,
                sameAs,
                isPartOf,
                organism,
                dbXrefs,
                dbXrefsStatistics,
                properties,
                search,
                distribution,
                downloadUrl,
                status,
                visibility,
                dateCreated,
                dateModified,
                datePublished
        );
    }
}
