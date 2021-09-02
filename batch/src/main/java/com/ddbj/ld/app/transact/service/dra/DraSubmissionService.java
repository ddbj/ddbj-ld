package com.ddbj.ld.app.transact.service.dra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.transact.dao.dra.DraAnalysisDao;
import com.ddbj.ld.app.transact.dao.dra.DraRunDao;
import com.ddbj.ld.app.transact.dao.dra.DraSubmissionDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.dra.submission.SUBMISSIONClass;
import com.ddbj.ld.data.beans.dra.submission.SubmissionConverter;
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
public class DraSubmissionService {

    private final JsonModule jsonModule;

    private final ObjectMapper objectMapper;

    private final DraSubmissionDao submissionDao;
    private final DraRunDao runDao;
    private final DraAnalysisDao analysisDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<IndexRequest> get(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var requests = new ArrayList<IndexRequest>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_SUBMISSION.start;
            var endTag    = XmlTagEnum.DRA_SUBMISSION.end;

            // 固定値
            // typeの設定
            var type = TypeEnum.SUBMISSION.getType();
            // Description に該当するデータは存在しないためsubmissionではnullを設定
            String description = null;
            // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
            List<SameAsBean> sameAs = null;
            // "DRA"固定
            var isPartOf = IsPartOfEnum.DRA.getIsPartOf();
            // 生物名とIDはSampleのみの情報であるため空情報を設定
            OrganismBean organism = null;

            // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
            var bioProjectType = TypeEnum.BIOPROJECT.type;
            var bioSampleType = TypeEnum.BIOSAMPLE.type;
            var experimentType = TypeEnum.EXPERIMENT.type;
            var runType = TypeEnum.RUN.type;
            var studyType = TypeEnum.STUDY.type;
            var sampleType = TypeEnum.SAMPLE.type;

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
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    var properties = this.getProperties(json, path);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // accesion取得
                    var identifier = properties.getAccession();

                    // Title取得
                    var title = properties.getTitle();

                    // name 設定
                    var name = properties.getAlias();

                    // dra-submission/[DES]RA??????
                    var url = this.jsonModule.getUrl(type, identifier);

                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    var dbXrefs = new ArrayList<DBXrefsBean>();

                    // bioproject, biosample, experiment, run, study, sample
                    var runList = this.runDao.selBySubmission(identifier);

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

                        if(!duplicatedCheck.contains(bioProjectId)) {
                            bioProjectDbXrefs.add(this.jsonModule.getDBXrefs(bioProjectId, bioProjectType));
                            duplicatedCheck.add(bioProjectId);
                        }

                        if(!duplicatedCheck.contains(bioSampleId)) {
                            bioSampleDbXrefs.add(this.jsonModule.getDBXrefs(bioSampleId, bioSampleType));
                            duplicatedCheck.add(bioSampleId);
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

                        if(!duplicatedCheck.contains(sampleId)) {
                            sampleDbXrefs.add(this.jsonModule.getDBXrefs(sampleId, sampleType));
                            duplicatedCheck.add(sampleId);
                        }
                    }

                    // analysis
                    var analysisDbXrefs = this.analysisDao.selBySubmission(identifier);

                    // bioproject→experiment→run→analysis→study→sampleの順でDbXrefsを格納していく
                    dbXrefs.addAll(bioProjectDbXrefs);
                    dbXrefs.addAll(experimentDbXrefs);
                    dbXrefs.addAll(runDbXrefs);
                    dbXrefs.addAll(analysisDbXrefs);
                    dbXrefs.addAll(studyDbXrefs);
                    dbXrefs.addAll(sampleDbXrefs);

                    // status, visibility、日付取得処理
                    var submission = this.submissionDao.select(identifier);
                    var status = null == submission ? null : submission.getStatus();
                    var visibility = null == submission ? null : submission.getVisibility();
                    var dateCreated = null == submission ? null : this.jsonModule.parseLocalDateTime(submission.getReceived());
                    var dateModified = null == submission ? null : this.jsonModule.parseLocalDateTime(submission.getUpdated());
                    var datePublished = null == submission ? null : this.jsonModule.parseLocalDateTime(submission.getPublished());

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

    private SUBMISSIONClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = SubmissionConverter.fromJsonString(json);

            return bean.getSubmission();
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