package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.common.SuppressedMetadataDao;
import com.ddbj.ld.app.transact.dao.sra.DRAAccessionDao;
import com.ddbj.ld.app.transact.dao.sra.DRALiveListDao;
import com.ddbj.ld.app.transact.dao.sra.SRARunDao;
import com.ddbj.ld.app.transact.dao.sra.SRASampleDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.sample.SAMPLEClass;
import com.ddbj.ld.data.beans.sra.sample.SampleConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SRASampleService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final MessageModule messageModule;
    private final SearchModule searchModule;

    private final SRARunDao runDao;
    private final SRASampleDao sampleDao;
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
            var startTag  = XmlTagEnum.SRA_SAMPLE.start;
            var endTag    = XmlTagEnum.SRA_SAMPLE.end;
            var type = TypeEnum.SAMPLE.getType();

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

                    if(null == bean) {
                        continue;
                    }

                    var identifier = bean.getIdentifier();
                    var doc = this.jsonModule.beanToJson(bean);
                    var indexRequest = new IndexRequest(type).id(identifier).source(doc, XContentType.JSON);
                    var updateRequest = new UpdateRequest(type, identifier).upsert(indexRequest).doc(doc, XContentType.JSON);

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
        var type = TypeEnum.SAMPLE.type;

        // suppressedからpublic, unpublishedになったデータはsuppressedテーブルから削除する
        var suppressedToPublicRecords = this.sampleDao.selSuppressedToPublic(date);
        var suppressedToUnpublishedRecords = this.sampleDao.selSuppressedToUnpublished(date);
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

        var toSuppressedRecords = this.sampleDao.selToSuppressed(date);
        var toUnpublishedRecords = this.sampleDao.selToUnpublished(date);

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

    public void printErrorInfo() {
        this.jsonModule.printErrorInfo(this.errorInfo);
    }

    public void validate(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_SAMPLE.start;
            var endTag    = XmlTagEnum.SRA_SAMPLE.end;

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
            this.messageModule.noticeErrorInfo(TypeEnum.SAMPLE.type, this.errorInfo);

        } else {
            var comment = String.format(
                    "%s\nsra-sample validation success.",
                    this.config.message.mention
            );

            this.messageModule.postMessage(this.config.message.channelId, comment);
        }

        this.errorInfo = new HashMap<>();
    }

    public void rename(final String date) {
        this.sampleDao.drop();
        this.sampleDao.rename(date);
        this.sampleDao.renameIndex(date);
    }

    public ArrayList<AccessionsBean> getDraAccessionList(
            final String path,
            final String submissionId
    ) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_SAMPLE.start;
            var endTag    = XmlTagEnum.SRA_SAMPLE.end;

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
                    var bioSampleId = null == ids ? null : ids.getPrimaryID().getContent();

                    var liveList = this.draLiveListDao.select(accession, submissionId);

                    var bean = new AccessionsBean(
                            accession,
                            liveList.getSubmission(),
                            StatusEnum.PUBLIC.status,
                            liveList.getUpdated(),
                            liveList.getUpdated(),
                            null,
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
                            bioSampleId,
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

    private SAMPLEClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = SampleConverter.fromJsonString(json);

            return bean.getSample();
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
        var type = TypeEnum.SAMPLE.getType();
        // "DRA"固定
        var isPartOf = IsPartOfEnum.SRA.getIsPartOf();

        // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
        var bioProjectType = TypeEnum.BIOPROJECT.type;
        var bioSampleType = TypeEnum.BIOSAMPLE.type;
        var submissionType = TypeEnum.SUBMISSION.type;
        var experimentType = TypeEnum.EXPERIMENT.type;
        var runType = TypeEnum.RUN.type;
        var studyType = TypeEnum.STUDY.type;

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
        var description = properties.getDescription();

        // name 取得
        String name = properties.getAlias();

        // sra-sample/[DES]RA??????
        var url = this.jsonModule.getUrl(type, identifier);

        // 自分と同値の情報を保持するデータを指定
        var externalID = properties.getIdentifiers().getExternalID();

        List<SameAsBean> sameAs = null;
        var bioSampleDbXrefs = new ArrayList<DBXrefsBean>();
        // 重複チェック用
        var duplicatedCheck = new HashSet<String>();

        if (externalID != null) {
            sameAs = new ArrayList<>();

            var bioSampleId = externalID.get(0).getContent();
            var bioSampleUrl = this.jsonModule.getUrl(bioSampleType, bioSampleId);

            sameAs.add(new SameAsBean(bioSampleId, bioSampleType, bioSampleUrl));
            bioSampleDbXrefs.add(new DBXrefsBean(bioSampleId, bioSampleType, bioSampleUrl));
            duplicatedCheck.add(bioSampleId);
        }

        // 生物名とIDを設定
        var samplename = properties.getSampleName();
        var organismName = samplename.getScientificName();
        var organismIdentifier = samplename.getTaxonID();
        var organism = this.jsonModule.getOrganism(organismName, organismIdentifier);

        var distribution = this.jsonModule.getDistribution(type, identifier);
        List<DownloadUrlBean> downloadUrl = null;

        var dbXrefs = new ArrayList<DBXrefsBean>();

        // bioproject, submission, experiment, run, study
        List<AccessionsBean> runList;

        if(identifier.startsWith("DR")) {
            runList = this.draAccessionDao.selRunBySample(identifier);
        } else {
            runList = this.runDao.selBySample(identifier);
        }

        var bioProjectDbXrefs = new ArrayList<DBXrefsBean>();
        var submissionDbXrefs = new ArrayList<DBXrefsBean>();
        var experimentDbXrefs = new ArrayList<DBXrefsBean>();
        var runDbXrefs = new ArrayList<DBXrefsBean>();
        var studyDbXrefs = new ArrayList<DBXrefsBean>();

        for(var run: runList) {
            var bioProjectId = run.getBioProject();
            var bioSampleId = run.getBioSample();
            var submissionId = run.getSubmission();
            var experimentId = run.getExperiment();
            var runId = run.getAccession();
            var studyId = run.getStudy();

            if(!duplicatedCheck.contains(bioProjectId)) {
                bioProjectDbXrefs.add(this.jsonModule.getDBXrefs(bioProjectId, bioProjectType));
                duplicatedCheck.add(bioProjectId);
            }

            if(!duplicatedCheck.contains(bioSampleId)) {
                bioSampleDbXrefs.add(this.jsonModule.getDBXrefs(bioSampleId, bioSampleType));
                duplicatedCheck.add(bioSampleId);
            }

            if(!duplicatedCheck.contains(submissionId)) {
                submissionDbXrefs.add(this.jsonModule.getDBXrefs(submissionId, submissionType));
                duplicatedCheck.add(submissionId);
            }

            if(!duplicatedCheck.contains(experimentId)) {
                experimentDbXrefs.add(this.jsonModule.getDBXrefs(experimentId, experimentType));
                duplicatedCheck.add(experimentId);
            }

            if(!duplicatedCheck.contains(runId)) {
                runDbXrefs.add(this.jsonModule.getDBXrefs(runId, runType));
                duplicatedCheck.add(runId);
            }

            if(!duplicatedCheck.contains(studyId)) {
                studyDbXrefs.add(this.jsonModule.getDBXrefs(studyId, studyType));
                duplicatedCheck.add(studyId);
            }
        }

        // bioproject→biosample→submission→experiment→run→→studyの順でDbXrefsを格納していく
        dbXrefs.addAll(bioProjectDbXrefs);
        dbXrefs.addAll(bioSampleDbXrefs);
        dbXrefs.addAll(submissionDbXrefs);
        dbXrefs.addAll(experimentDbXrefs);
        dbXrefs.addAll(runDbXrefs);
        dbXrefs.addAll(studyDbXrefs);

        AccessionsBean sample;

        if(identifier.startsWith("DR")) {
            if(submissionDbXrefs.size() > 0) {
                sample = this.draAccessionDao.one(identifier, submissionDbXrefs.get(0).getIdentifier());
            } else {
                sample = this.draAccessionDao.select(identifier);
            }
        } else {
            sample = this.sampleDao.select(identifier);
        }
        // status, visibility、日付取得処理
        var status = null == sample ? StatusEnum.PUBLIC.status : sample.getStatus();
        var visibility = null == sample ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : sample.getVisibility();
        var dateCreated = null == sample ? null : this.jsonModule.parseLocalDateTime(sample.getReceived());
        var dateModified = null == sample ? null : this.jsonModule.parseLocalDateTime(sample.getUpdated());
        var datePublished = null == sample ? null : this.jsonModule.parseLocalDateTime(sample.getPublished());

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
                properties,
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
