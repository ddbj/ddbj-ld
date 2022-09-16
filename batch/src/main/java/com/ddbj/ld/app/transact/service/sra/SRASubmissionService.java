package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.primary.common.SuppressedMetadataDao;
import com.ddbj.ld.app.transact.dao.primary.sra.*;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.submission.SUBMISSIONClass;
import com.ddbj.ld.data.beans.sra.submission.SubmissionConverter;
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
public class SRASubmissionService {

    private final JsonModule jsonModule;
    private final MessageModule messageModule;
    private final SearchModule searchModule;

    private final SRASubmissionDao submissionDao;
    private final SRARunDao runDao;
    private final SRAAnalysisDao analysisDao;
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
            var startTag  = XmlTagEnum.SRA_SUBMISSION.start;
            var endTag    = XmlTagEnum.SRA_SUBMISSION.end;

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
                if(line.contains(endTag) || line.matches("^(<SUBMISSION).*(/>)$")) {
                    var json = this.jsonModule.xmlToJson(sb.toString());
                    var bean = this.getBean(json, path);

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

                    var updateRequest = this.jsonModule.getUpdateRequest(bean);

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
        var type = TypeEnum.SUBMISSION.type;

        // suppressedからpublic, unpublishedになったデータはsuppressedテーブルから削除する
        var suppressedToPublicRecords = this.submissionDao.selSuppressedToPublic(date);
        var suppressedToUnpublishedRecords = this.submissionDao.selSuppressedToUnpublished(date);
        var suppressedArgs = new ArrayList<Object[]>();

        for (var submission : suppressedToPublicRecords) {
            suppressedArgs.add(new Object[] {
               submission.getAccession()
            });
        }

        for (var submission : suppressedToUnpublishedRecords) {
            suppressedArgs.add(new Object[] {
                    submission.getAccession()
            });
        }

        this.suppressedMetadataDao.bulkDelete(suppressedArgs);

        var toSuppressedRecords = this.submissionDao.selToSuppressed(date);
        var toUnpublishedRecords = this.submissionDao.selToUnpublished(date);

        var deleteRequests = new ArrayList<DeleteRequest>();
        var getRequests = new MultiGetRequest();

        for(var submission : toSuppressedRecords) {
            var identifier = submission.getAccession();
            deleteRequests.add(new DeleteRequest(type).id(identifier));

            getRequests.add(new MultiGetRequest.Item(type, identifier));
        }

        for(var submission : toUnpublishedRecords) {
            var identifier = submission.getAccession();
            deleteRequests.add(new DeleteRequest(type).id(identifier));
        }

        // suppressedとなったレコードをSuppressedテーブルに登録
        if(getRequests.getItems().size() > 0) {
            var getResponses = this.searchModule.get(getRequests);
            var submissionList = new ArrayList<Object[]>();

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

                submissionList.add(new Object[] {
                        response.getId(),
                        type,
                        json
                });
            }

            this.suppressedMetadataDao.bulkInsert(submissionList);
        }

        return deleteRequests;
    }

    public void validate(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_SUBMISSION.start;
            var endTag    = XmlTagEnum.SRA_SUBMISSION.end;

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
                if(line.contains(endTag) || line.matches("^(<SUBMISSION).*(/>)$")) {
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
            this.messageModule.noticeErrorInfo(TypeEnum.SUBMISSION.type, this.errorInfo);

        }

        this.errorInfo = new HashMap<>();
    }

    public void rename(final String date) {
        this.submissionDao.drop();
        this.submissionDao.rename(date);
        this.submissionDao.renameIndex(date);
    }

    public AccessionsBean getDraAccession(
            final String path,
            final String submissionId
    ) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_SUBMISSION.start;
            var endTag    = XmlTagEnum.SRA_SUBMISSION.end;
            SUBMISSIONClass properties = null;

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                if(line.contains(endTag) || line.matches("^(<SUBMISSION).*(/>)$")) {
                    var json = this.jsonModule.xmlToJson(sb.toString());
                    properties = this.getProperties(json, path);

                    break;
                }
            }

            if(null == properties) {
                var message = String.format("Converting is failed.:%s", path);
                log.error(message);

                throw new DdbjException(message);
            }

            var accession = properties.getAccession();
            var liveList = this.draLiveListDao.select(accession, submissionId);

            if(null == liveList) {
                log.warn("Can't get livelist: {}", accession);

                return null;
            }

            return new AccessionsBean(
                    accession,
                    accession,
                    StatusEnum.PUBLIC.status,
                    liveList.getUpdated(),
                    liveList.getUpdated(),
                    null == properties.getSubmissionDate() ? liveList.getUpdated() : properties.getSubmissionDate().toLocalDateTime(),
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
                    null,
                    null,
                    null,
                    null
            );

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    private SUBMISSIONClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = SubmissionConverter.fromJsonString(json);

            return bean.getSubmission();
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
        var type = TypeEnum.SUBMISSION.getType();
        // Description に該当するデータは存在しないためsubmissionではnullを設定
        String description = null;
        // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
        List<SameAsBean> sameAs = null;
        // "DRA"固定
        var isPartOf = IsPartOfEnum.SRA.getIsPartOf();
        // 生物名とIDはSampleのみの情報であるため空情報を設定
        OrganismBean organism = null;

        // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
        var bioProjectType = TypeEnum.BIOPROJECT.type;
        var bioSampleType = TypeEnum.BIOSAMPLE.type;
        var experimentType = TypeEnum.EXPERIMENT.type;
        var runType = TypeEnum.RUN.type;
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

        // name 設定
        var name = properties.getAlias();

        // sra-submission/[DES]RA??????
        var url = this.jsonModule.getUrl(type, identifier);

        var search = this.jsonModule.beanToJson(properties);

        var distribution = this.jsonModule.getDistribution(type, identifier);

        List<DownloadUrlBean> downloadUrl;
        var prefix = identifier.substring(0, 6);

        downloadUrl = new ArrayList<>();

        var ftpHostname = "ftp.ddbj.nig.ac.jp";
        var ftpPath = "/ddbj_database/dra/fastq/" + prefix + "/" + identifier;

        downloadUrl.add(new DownloadUrlBean(
                "meta",
                "Submitted metadata",
                "https://ddbj.nig.ac.jp/public" + ftpPath,
                "ftp://" + ftpHostname + ftpPath
        ));

        var dbXrefs = new ArrayList<DBXrefsBean>();

        // bioproject, biosample, experiment, run, study, sample
        List<AccessionsBean> runList;

        if(identifier.startsWith("DR")) {
            runList = this.draAccessionDao.selRunBySubmission(identifier);
        } else {
            runList = this.runDao.selBySubmission(identifier);
        }

        // analysisはbioproject, studyとしか紐付かないようで取得できない
        var bioProjectDbXrefs = new ArrayList<DBXrefsBean>();
        var bioSampleDbXrefs = new ArrayList<DBXrefsBean>();
        var experimentDbXrefs = new ArrayList<DBXrefsBean>();
        var runDbXrefs = new ArrayList<DBXrefsBean>();
        var studyDbXrefs = new ArrayList<DBXrefsBean>();
        var sampleDbXrefs = new ArrayList<DBXrefsBean>();

        // 重複チェック用
        var duplicatedCheck = new HashSet<String>();

        for(var run : runList) {
            var bioProjectId = run.getBioProject();
            var bioSampleId = run.getBioSample();
            var experimentId = run.getExperiment();
            var runId = run.getAccession();
            var studyId = run.getStudy();
            var sampleId = run.getSample();

            if(null != bioProjectId && !duplicatedCheck.contains(bioProjectId)) {
                bioProjectDbXrefs.add(this.jsonModule.getDBXrefs(bioProjectId, bioProjectType));
                duplicatedCheck.add(bioProjectId);
            }

            if(null != bioSampleId && !duplicatedCheck.contains(bioSampleId)) {
                bioSampleDbXrefs.add(this.jsonModule.getDBXrefs(bioSampleId, bioSampleType));
                duplicatedCheck.add(bioSampleId);
            }

            if(null != experimentId && !duplicatedCheck.contains(experimentId)) {
                experimentDbXrefs.add(this.jsonModule.getDBXrefs(experimentId, experimentType));
                duplicatedCheck.add(experimentId);
            }

            if(null != runId && !duplicatedCheck.contains(runId)) {
                runDbXrefs.add(this.jsonModule.getDBXrefs(runId, runType));
                duplicatedCheck.add(runId);
            }

            if(null != studyId && !duplicatedCheck.contains(studyId)) {
                studyDbXrefs.add(this.jsonModule.getDBXrefs(studyId, studyType));
                duplicatedCheck.add(studyId);
            }

            if(null != sampleId && !duplicatedCheck.contains(sampleId)) {
                sampleDbXrefs.add(this.jsonModule.getDBXrefs(sampleId, sampleType));
                duplicatedCheck.add(sampleId);
            }
        }

        // analysis
        List<DBXrefsBean> analysisDbXrefs;

        if(identifier.startsWith("DR")) {
            analysisDbXrefs =  this.draAccessionDao.selAnalysisBySubmission(identifier);
        } else {
            analysisDbXrefs = this.analysisDao.selBySubmission(identifier);
        }

        // bioproject→experiment→run→analysis→study→sampleの順でDbXrefsを格納していく
        dbXrefs.addAll(bioProjectDbXrefs);
        dbXrefs.addAll(bioSampleDbXrefs);
        dbXrefs.addAll(experimentDbXrefs);
        dbXrefs.addAll(runDbXrefs);
        dbXrefs.addAll(analysisDbXrefs);
        dbXrefs.addAll(studyDbXrefs);
        dbXrefs.addAll(sampleDbXrefs);

        AccessionsBean submission;

        if(identifier.startsWith("DR")) {
            submission =  this.draAccessionDao.one(identifier, identifier);
        } else {
            submission = this.submissionDao.select(identifier);
        }

        if (null == submission) {
            log.warn("Can't get submission record: {}", identifier);

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

        // status, visibility、日付取得処理
        var status = null == submission.getStatus() ? StatusEnum.PUBLIC.status : submission.getStatus();
        var visibility = null == submission.getVisibility() ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : submission.getVisibility();

        var offset = identifier.startsWith("DR") ? ZoneOffset.ofHours(9) : ZoneOffset.ofHours(0);
        var dateCreated = this.jsonModule.parseLocalDateTime(submission.getReceived(), offset);
        var dateModified = this.jsonModule.parseLocalDateTime(submission.getUpdated(), offset);
        var datePublished = this.jsonModule.parseLocalDateTime(submission.getPublished(), offset);

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
