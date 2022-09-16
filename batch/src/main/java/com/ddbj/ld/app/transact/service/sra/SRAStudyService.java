package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.primary.common.SuppressedMetadataDao;
import com.ddbj.ld.app.transact.dao.primary.sra.*;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.study.STUDYClass;
import com.ddbj.ld.data.beans.sra.study.StudyConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SRAStudyService {

    private final JsonModule jsonModule;
    private final MessageModule messageModule;
    private final SearchModule searchModule;

    private final SRARunDao runDao;
    private final SRAAnalysisDao analysisDao;
    private final SRAStudyDao studyDao;
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
            var startTag  = XmlTagEnum.SRA_STUDY.start;
            var endTag    = XmlTagEnum.SRA_STUDY.end;

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
        var type = TypeEnum.STUDY.type;

        // suppressedからpublic, unpublishedになったデータはsuppressedテーブルから削除する
        var suppressedToPublicRecords = this.studyDao.selSuppressedToPublic(date);
        var suppressedToUnpublishedRecords = this.studyDao.selSuppressedToUnpublished(date);
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

        var toSuppressedRecords = this.studyDao.selToSuppressed(date);
        var toUnpublishedRecords = this.studyDao.selToUnpublished(date);

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
            var startTag  = XmlTagEnum.SRA_STUDY.start;
            var endTag    = XmlTagEnum.SRA_STUDY.end;

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

    public void noticeErrorInfo() {
        if(this.errorInfo.size() > 0) {
            this.messageModule.noticeErrorInfo(TypeEnum.STUDY.type, this.errorInfo);
        }

        this.errorInfo = new HashMap<>();
    }

    public void rename(final String date) {
        this.studyDao.drop();
        this.studyDao.rename(date);
        this.studyDao.renameIndex(date);
    }

    public ArrayList<AccessionsBean> getDraAccessionList(
            final String path,
            final String submissionId
    ) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_STUDY.start;
            var endTag    = XmlTagEnum.SRA_STUDY.end;

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
                    var ids = properties.getIdentifiers();
                    var bioProjectId = null == ids ? null : ids.getPrimaryID().getContent();

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
                            null,
                            null,
                            null,
                            (byte) 1,
                            null,
                            null,
                            liveList.getMd5sum(),
                            null,
                            bioProjectId,
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

    private STUDYClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = StudyConverter.fromJsonString(json);

            return bean.getStudy();
        } catch (IOException e) {
            var message = e.getLocalizedMessage()
                    .replaceAll("\n at.*.", "")
                    .replaceAll("\\(.*.", "");
            log.error("Converting metadata to bean is failed. xml path: {}, json:{}, message: {}", path, json, message, e);

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
        var type = TypeEnum.STUDY.getType();
        // "DRA"固定
        var isPartOf = IsPartOfEnum.SRA.getIsPartOf();
        // 生物名とIDはSampleのみの情報であるため空情報を設定
        OrganismBean organism = null;

        // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
        var bioProjectType = TypeEnum.BIOPROJECT.type;
        var bioSampleType = TypeEnum.BIOSAMPLE.type;
        var submissionType = TypeEnum.SUBMISSION.type;
        var experimentType = TypeEnum.EXPERIMENT.type;
        var runType = TypeEnum.RUN.type;
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
        var descriptor = properties.getDescriptor();
        var title = descriptor.getStudyTitle();

        // Description 取得
        var description = descriptor.getStudyDescription();

        // name 取得
        var name = properties.getAlias();

        // sra-study/[DES]RA??????
        var url = this.jsonModule.getUrl(type, identifier);

        // 自分と同値の情報を保持するBioProjectを指定
        var externalid = properties.getIdentifiers().getExternalID();
        List<SameAsBean> sameAs = null;
        var dbXrefs = new ArrayList<DBXrefsBean>();
        // 重複チェック用
        var duplicatedCheck = new HashSet<String>();

        if (externalid != null) {
            // 自分と同値(Sample)の情報を保持するデータを指定
            sameAs = new ArrayList<>();

            var bioProjectId = externalid.get(0).getContent();
            var bioProjectUrl = this.jsonModule.getUrl(bioProjectType, bioProjectId);

            sameAs.add(new SameAsBean(bioProjectId, bioProjectType, bioProjectUrl));
            dbXrefs.add(new DBXrefsBean(bioProjectId, bioProjectType, bioProjectUrl));
            duplicatedCheck.add(bioProjectId);
        }

        var search = this.jsonModule.beanToJson(properties);

        var distribution = this.jsonModule.getDistribution(type, identifier);

        // biosample, submission, experiment, run, sample
        List<AccessionsBean> runList;

        if(identifier.startsWith("DR")) {
            runList = this.draAccessionDao.selRunByStudy(identifier);
        } else {
            runList = this.runDao.selByStudy(identifier);
        }

        List<DBXrefsBean> bioSampleDbXrefs = new ArrayList<>();
        var bioProjectDbXrefs = new ArrayList<DBXrefsBean>();
        var submissionDbXrefs = new ArrayList<DBXrefsBean>();
        var experimentDbXrefs = new ArrayList<DBXrefsBean>();
        var runDbXrefs = new ArrayList<DBXrefsBean>();
        List<DBXrefsBean> sampleDbXrefs = new ArrayList<>();
        // downloadURLを作るときに再利用するため、Submissionだけここ
        String submissionId = null;

        for(var run: runList) {
            var bioProjectId = run.getBioProject();
            var bioSampleId = run.getBioSample();
            submissionId = run.getSubmission();
            var experimentId = run.getExperiment();
            var runId = run.getAccession();
            var sampleId = run.getSample();

            if(null != bioProjectId && !duplicatedCheck.contains(bioProjectId)) {
                bioProjectDbXrefs.add(this.jsonModule.getDBXrefs(bioProjectId, bioProjectType));
                duplicatedCheck.add(bioProjectId);
            }

            if(null != bioSampleId && !duplicatedCheck.contains(bioSampleId)) {
                bioSampleDbXrefs.add(this.jsonModule.getDBXrefs(bioSampleId, bioSampleType));
                duplicatedCheck.add(bioSampleId);
            }

            if(null != submissionId && !duplicatedCheck.contains(submissionId)) {
                submissionDbXrefs.add(this.jsonModule.getDBXrefs(submissionId, submissionType));
                duplicatedCheck.add(submissionId);
            }

            if(null != experimentId && !duplicatedCheck.contains(experimentId)) {
                experimentDbXrefs.add(this.jsonModule.getDBXrefs(experimentId, experimentType));
                duplicatedCheck.add(experimentId);
            }

            if(null != runId && !duplicatedCheck.contains(runId)) {
                runDbXrefs.add(this.jsonModule.getDBXrefs(runId, runType));
                duplicatedCheck.add(runId);
            }

            if(null != sampleId && !duplicatedCheck.contains(sampleId)) {
                sampleDbXrefs.add(this.jsonModule.getDBXrefs(sampleId, sampleType));
                duplicatedCheck.add(sampleId);
            }
        }

        // bioproject→submission→experiment→run→analysis→sampleの順でDbXrefsを格納していく
        List<DBXrefsBean> analysisDbXrefs;

        if(identifier.startsWith("DR")) {
            analysisDbXrefs = this.draAccessionDao.selAnalysisByStudy(identifier);
        } else {
            analysisDbXrefs = this.analysisDao.selByStudy(identifier);
        }

        dbXrefs.addAll(bioProjectDbXrefs);
        dbXrefs.addAll(bioSampleDbXrefs);
        dbXrefs.addAll(submissionDbXrefs);
        dbXrefs.addAll(experimentDbXrefs);
        dbXrefs.addAll(runDbXrefs);
        dbXrefs.addAll(analysisDbXrefs);
        dbXrefs.addAll(sampleDbXrefs);

        AccessionsBean study;

        if(identifier.startsWith("DR")) {
            if(submissionDbXrefs.size() > 0) {
                study = this.draAccessionDao.one(identifier, submissionDbXrefs.get(0).getIdentifier());
            } else {
                study = this.draAccessionDao.select(identifier);
            }
        } else {
            study = this.studyDao.select(identifier);
        }

        if(null == study) {
            log.warn("Can't get study record: {}", identifier);

            return null;
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

        List<DownloadUrlBean> downloadUrl = new ArrayList<>();

        var prefix = null == submissionId ? null : submissionId.substring(0, 6);
        var fileName = null == submissionId ? null : submissionId + ".study.xml";
        var ftpPath = "/ddbj_database/dra/fastq/" + prefix + "/" + submissionId + "/" + fileName;

        downloadUrl.add(new DownloadUrlBean(
                "meta",
                fileName,
                "https://ddbj.nig.ac.jp/public" + ftpPath,
                "ftp://ftp.ddbj.nig.ac.jp" + ftpPath
        ));

        // status, visibility、日付取得処理
        var status = null == study.getStatus() ? StatusEnum.PUBLIC.status : study.getStatus();
        var visibility = null == study.getVisibility() ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : study.getVisibility();

        var offset = identifier.startsWith("DR") ? ZoneOffset.ofHours(9) : ZoneOffset.ofHours(0);
        var dateCreated = this.jsonModule.parseLocalDateTime(study.getReceived(), offset);
        var dateModified = this.jsonModule.parseLocalDateTime(study.getUpdated(), offset);
        var datePublished = this.jsonModule.parseLocalDateTime(study.getPublished(), offset);

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
