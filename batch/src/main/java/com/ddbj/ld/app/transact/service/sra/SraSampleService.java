package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.transact.dao.sra.SraRunDao;
import com.ddbj.ld.app.transact.dao.sra.SraSampleDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.sra.sample.SAMPLEClass;
import com.ddbj.ld.data.beans.sra.sample.SampleConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.XML;
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
public class SraSampleService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final MessageModule messageModule;

    private final ObjectMapper objectMapper;

    private final SraRunDao runDao;
    private final SraSampleDao sampleDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<IndexRequest> get(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var requests = new ArrayList<IndexRequest>();

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_SAMPLE.start;
            var endTag    = XmlTagEnum.SRA_SAMPLE.end;

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
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    var properties = this.getProperties(json, path);

                    if(null == properties) {
                        continue;
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

                        var sampleId = externalID.get(0).getContent();
                        var sampleUrl = this.jsonModule.getUrl(type, sampleId);

                        sameAs.add(new SameAsBean(sampleId, bioSampleType, sampleUrl));
                        bioSampleDbXrefs.add(new DBXrefsBean(sampleId, bioSampleType, sampleUrl));
                        duplicatedCheck.add(sampleId);
                    }

                    // 生物名とIDを設定
                    var samplename = properties.getSampleName();
                    var organismName = samplename.getScientificName();
                    var organismIdentifier = samplename.getTaxonID();
                    var organism = this.jsonModule.getOrganism(organismName, organismIdentifier);

                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    var dbXrefs = new ArrayList<DBXrefsBean>();

                    // bioproject, submission, experiment, run, study
                    var runList = this.runDao.selBySample(identifier);
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

                    var sample = this.sampleDao.select(identifier);
                    // status, visibility、日付取得処理
                    var status = null == sample ? StatusEnum.LIVE.status : sample.getStatus();
                    var visibility = null == sample ? VisibilityEnum.PUBLIC.visibility : sample.getVisibility();
                    var dateCreated = null == sample ? null : this.jsonModule.parseLocalDateTime(sample.getReceived());
                    var dateModified = null == sample ? null : this.jsonModule.parseLocalDateTime(sample.getUpdated());
                    var datePublished = null == sample ? null : this.jsonModule.parseLocalDateTime(sample.getPublished());

                    var bean = new JsonBean(
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
                            status,
                            visibility,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    requests.add(new IndexRequest(type).id(identifier).source(this.objectMapper.writeValueAsString(bean), XContentType.JSON));
                }
            }

            return requests;

        } catch (IOException e) {
            log.error("Not exists file:{}", path, e);

            return null;
        }
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
                    var json = XML.toJSONObject(sb.toString()).toString();
                    this.getProperties(json, path);
                }
            }

        } catch (IOException e) {
            log.error("Not exists file:{}", path, e);
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

    private SAMPLEClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = SampleConverter.fromJsonString(json);

            return bean.getSample();
        } catch (IOException e) {
            log.debug("Converting metadata to bean is failed. xml path: {}, json:{}", path, json, e);

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
}
