package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.transact.dao.sra.SraExperimentDao;
import com.ddbj.ld.app.transact.dao.sra.SraRunDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.experiment.EXPERIMENTClass;
import com.ddbj.ld.data.beans.sra.experiment.ExperimentConverter;
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
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SraExperimentService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final MessageModule messageModule;

    private final ObjectMapper objectMapper;

    private final SraExperimentDao experimentDao;
    private final SraRunDao runDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<IndexRequest> get(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var requests = new ArrayList<IndexRequest>();

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_EXPERIMENT.start;
            var endTag    = XmlTagEnum.SRA_EXPERIMENT.end;

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

                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    var dbXrefs = new ArrayList<DBXrefsBean>();

                    // experimentはrun, analysis以外一括で取得できる
                    // bioproject、biosample、submission、study、sample、status、visibility、date_created、date_modified、date_published
                    var experiment = this.experimentDao.select(identifier);

                    // analysisはbioproject, studyとしか紐付かないようで取得できない

                    if(null != experiment) {
                        dbXrefs.add(this.jsonModule.getDBXrefs(experiment.getBioProject(), bioProjectType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(experiment.getBioSample(), bioSampleType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(experiment.getSubmission(), submissionType));
                        dbXrefs.addAll(this.runDao.selByExperiment(identifier));
                        dbXrefs.add(this.jsonModule.getDBXrefs(experiment.getStudy(), studyType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(experiment.getSample(), sampleType));
                    }

                    // status, visibility、日付取得処理
                    var status = null == experiment ? StatusEnum.PUBLIC.status : experiment.getStatus();
                    var visibility = null == experiment ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : experiment.getVisibility();
                    var dateCreated = null == experiment ? null : this.jsonModule.parseLocalDateTime(experiment.getReceived());
                    var dateModified = null == experiment ? null : this.jsonModule.parseLocalDateTime(experiment.getUpdated());
                    var datePublished = null == experiment ? null : this.jsonModule.parseLocalDateTime(experiment.getPublished());

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
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
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
                    var json = XML.toJSONObject(sb.toString()).toString();
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

        } else {
            var comment = String.format(
                    "%s\nsra-experiment validation success.",
                    this.config.message.mention
            );

            this.messageModule.postMessage(this.config.message.channelId, comment);
        }

        this.errorInfo = new HashMap<>();
    }

    private EXPERIMENTClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = ExperimentConverter.fromJsonString(json);

            return  bean.getExperiment();
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
}
