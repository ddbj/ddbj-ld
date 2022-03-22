package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.primary.common.SuppressedMetadataDao;
import com.ddbj.ld.app.transact.dao.primary.sra.DRAAccessionDao;
import com.ddbj.ld.app.transact.dao.primary.sra.DRALiveListDao;
import com.ddbj.ld.app.transact.dao.primary.sra.SRAExperimentDao;
import com.ddbj.ld.app.transact.dao.primary.sra.SRARunDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.experiment.EXPERIMENTClass;
import com.ddbj.ld.data.beans.sra.experiment.ExperimentConverter;
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
public class SRAExperimentService {

    private final JsonModule jsonModule;
    private final MessageModule messageModule;
    private final SearchModule searchModule;

    private final SRAExperimentDao experimentDao;
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
            var startTag  = XmlTagEnum.SRA_EXPERIMENT.start;
            var endTag    = XmlTagEnum.SRA_EXPERIMENT.end;

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
        var type = TypeEnum.EXPERIMENT.type;

        // suppressedからpublic, unpublishedになったデータはsuppressedテーブルから削除する
        var suppressedToPublicRecords = this.experimentDao.selSuppressedToPublic(date);
        var suppressedToUnpublishedRecords = this.experimentDao.selSuppressedToUnpublished(date);
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

        var toSuppressedRecords = this.experimentDao.selToSuppressed(date);
        var toUnpublishedRecords = this.experimentDao.selToUnpublished(date);

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
            var startTag  = XmlTagEnum.SRA_EXPERIMENT.start;
            var endTag    = XmlTagEnum.SRA_EXPERIMENT.end;

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
            this.messageModule.noticeErrorInfo(TypeEnum.EXPERIMENT.type, this.errorInfo);

        }

        this.errorInfo = new HashMap<>();
    }

    public void rename(final String date) {
        this.experimentDao.drop();
        this.experimentDao.rename(date);
        this.experimentDao.renameIndex(date);
    }

    public HashMap<String, AccessionsBean> getDraAccessionMap(
            final String path,
            final String submissionId
    ) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_EXPERIMENT.start;
            var endTag    = XmlTagEnum.SRA_EXPERIMENT.end;

            var accessionsMap = new HashMap<String, AccessionsBean>();

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

                    var studyRef= properties.getStudyRef();
                    var studyId = null == studyRef ? null : studyRef.getAccession();
                    var studyIds = null == studyRef ? null : studyRef.getIdentifiers();
                    var bioProjectId = null == studyIds ? null : studyIds.getPrimaryID().getContent();
                    var sampleDescriber   = null == properties.getDesign() ? null : properties.getDesign().getSampleDescriptor();
                    var sampleId = null == sampleDescriber ? null : sampleDescriber.getAccession();
                    var sampleIds = null == sampleDescriber ? null : sampleDescriber.getIdentifiers();
                    var bioSampleId = null == sampleIds.getPrimaryID() ? null : sampleIds.getPrimaryID().getContent();

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
                            sampleId,
                            studyId,
                            (byte) 1,
                            null,
                            null,
                            liveList.getMd5sum(),
                            bioSampleId,
                            bioProjectId,
                            null,
                            null,
                            null
                    );

                    accessionsMap.put(accession, bean);

                }
            }

            return accessionsMap;

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    private EXPERIMENTClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = ExperimentConverter.fromJsonString(json);

            return  bean.getExperiment();
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
        // typeの設定
        var type = TypeEnum.EXPERIMENT.getType();
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

        // Description 取得
        var design = properties.getDesign();
        var librarydescriptor = design.getLibraryDescriptor();
        var targetloci = librarydescriptor.getTargetedLoci();


        String description = null;
        if (targetloci != null) {
            var locus = targetloci.getLocus();
            description = locus.get(0).getDescription();
        }

        // name 取得
        var name = properties.getAlias();

        // sra-experiment/[DES]RA??????
        var url = this.jsonModule.getUrl(type, identifier);

        var search = this.jsonModule.beanToJson(properties);

        var distribution = this.jsonModule.getDistribution(type, identifier);
        List<DownloadUrlBean> downloadUrl = null;

        var dbXrefs = new ArrayList<DBXrefsBean>();

        // experimentはrun, analysis以外一括で取得できるが、万が一BioProject,BioSample,Study,Sampleが存在しないケースも考えておく
        // bioproject、biosample、submission、study、sample、status、visibility、date_created、date_modified、date_published
        AccessionsBean experiment;
        List<DBXrefsBean> runIdList;

        if(identifier.startsWith("DR")) {
            experiment = this.draAccessionDao.select(identifier);
            runIdList = this.draAccessionDao.selRunByExperiment(identifier);
        } else {
            experiment = this.experimentDao.select(identifier);
            runIdList = this.runDao.selByExperiment(identifier);
        }

        // analysisはbioproject, studyとしか紐付かないようで取得できない

        var studyRef = properties.getStudyRef();
        var sampleDescriptor = null == properties.getDesign() ? null : properties.getDesign().getSampleDescriptor();

        var submissionId = null == experiment ? null : experiment.getSubmission();
        var bioProjectId = null == studyRef || null == studyRef.getIdentifiers() || null == studyRef.getIdentifiers().getPrimaryID() ? null : studyRef.getIdentifiers().getPrimaryID().getContent();
        var bioSampleId = null == sampleDescriptor || null == sampleDescriptor.getIdentifiers() || null == sampleDescriptor.getIdentifiers().getPrimaryID() ? null: sampleDescriptor.getIdentifiers().getPrimaryID().getContent();
        var studyId = null == studyRef ? null : studyRef.getAccession();
        var sampleId = null == sampleDescriptor ? null : sampleDescriptor.getAccession();

        if(null != submissionId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(submissionId, submissionType));

            var prefix = submissionId.substring(0, 6);
            var fileName = submissionId + ".experiment.xml";
            var ftpPath = "/ddbj_database/dra/fastq/" + prefix + "/" + submissionId + "/" + fileName;

            downloadUrl = new ArrayList<>();

            downloadUrl.add(new DownloadUrlBean(
                    "meta",
                    fileName,
                    "https://ddbj.nig.ac.jp/public" + ftpPath,
                    "ftp://ftp.ddbj.nig.ac.jp" + ftpPath
            ));
        }

        if(null != bioProjectId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(bioProjectId, bioProjectType));
        }

        if(null != bioSampleId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(bioSampleId, bioSampleType));
        }

        if(null != runIdList) {
            dbXrefs.addAll(runIdList);
        }

        if(null != studyId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(studyId, studyType));
        }

        if(null != sampleId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(sampleId, sampleType));
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
        var status = null == experiment || null == experiment.getStatus() ? StatusEnum.PUBLIC.status : experiment.getStatus();
        var visibility = null == experiment || null == experiment.getVisibility() ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : experiment.getVisibility();
        var dateCreated = this.jsonModule.parseLocalDateTime(null == experiment ? null : experiment.getReceived());
        var dateModified = this.jsonModule.parseLocalDateTime(null == experiment ? null : experiment.getUpdated());
        var datePublished = this.jsonModule.parseLocalDateTime(null == experiment ? null : experiment.getPublished());

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
