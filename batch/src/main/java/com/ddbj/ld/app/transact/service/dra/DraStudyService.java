package com.ddbj.ld.app.transact.service.dra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.transact.dao.dra.DraAnalysisDao;
import com.ddbj.ld.app.transact.dao.dra.DraRunDao;
import com.ddbj.ld.app.transact.dao.dra.DraStudyDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.dra.study.STUDYClass;
import com.ddbj.ld.data.beans.dra.study.StudyConverter;
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
public class DraStudyService {

    private final JsonModule jsonModule;

    private final ObjectMapper objectMapper;

    private final DraRunDao runDao;
    private final DraAnalysisDao analysisDao;
    private final DraStudyDao studyDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<IndexRequest> get(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var requests = new ArrayList<IndexRequest>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_STUDY.start;
            var endTag    = XmlTagEnum.DRA_STUDY.end;

            // 固定値
            var type = TypeEnum.STUDY.getType();
            // "DRA"固定
            var isPartOf = IsPartOfEnum.DRA.getIsPartOf();
            // 生物名とIDはSampleのみの情報であるため空情報を設定
            OrganismBean organism = null;

            // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
            var bioProjectType = TypeEnum.BIOPROJECT.type;
            var bioSampleType = TypeEnum.BIOSAMPLE.type;
            var submissionType = TypeEnum.SUBMISSION.type;
            var experimentType = TypeEnum.EXPERIMENT.type;
            var runType = TypeEnum.RUN.type;
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
                if(line.contains(endTag)) {
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
                    var descriptor = properties.getDescriptor();
                    var title = descriptor.getStudyTitle();

                    // Description 取得
                    var description = descriptor.getStudyDescription();

                    // name 取得
                    var name = properties.getAlias();

                    // dra-study/[DES]RA??????
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

                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    // biosample, submission, experiment, run, sample
                    var runList = this.runDao.selByStudy(identifier);
                    List<DBXrefsBean> bioSampleDbXrefs = new ArrayList<>();
                    var bioProjectDbXrefs = new ArrayList<DBXrefsBean>();
                    var submissionDbXrefs = new ArrayList<DBXrefsBean>();
                    var experimentDbXrefs = new ArrayList<DBXrefsBean>();
                    var runDbXrefs = new ArrayList<DBXrefsBean>();
                    List<DBXrefsBean> sampleDbXrefs = new ArrayList<>();

                    for(var run: runList) {
                        var bioProjectId = run.getBioProject();
                        var bioSampleId = run.getBioSample();
                        var submissionId = run.getSubmission();
                        var experimentId = run.getExperiment();
                        var runId = run.getAccession();
                        var sampleId = run.getSample();

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

                        if(!duplicatedCheck.contains(sampleId)) {
                            sampleDbXrefs.add(this.jsonModule.getDBXrefs(sampleId, sampleType));
                            duplicatedCheck.add(sampleId);
                        }
                    }

                    // bioproject→submission→experiment→run→analysis→sampleの順でDbXrefsを格納していく
                    dbXrefs.addAll(bioProjectDbXrefs);
                    dbXrefs.addAll(submissionDbXrefs);
                    dbXrefs.addAll(experimentDbXrefs);
                    dbXrefs.addAll(runDbXrefs);
                    dbXrefs.addAll(this.analysisDao.selByStudy(identifier));
                    dbXrefs.addAll(sampleDbXrefs);

                    var study = this.studyDao.select(identifier);
                    // status, visibility、日付取得処理
                    var status = null == study ? null : study.getStatus();
                    var visibility = null == study ? null : study.getVisibility();
                    var dateCreated = null == study ? null : this.jsonModule.parseLocalDateTime(study.getReceived());
                    var dateModified = null == study ? null : this.jsonModule.parseLocalDateTime(study.getUpdated());
                    var datePublished = null == study ? null : this.jsonModule.parseLocalDateTime(study.getPublished());

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

    private STUDYClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = StudyConverter.fromJsonString(json);

            return bean.getStudy();
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