package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.transact.dao.sra.SraRunDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.run.RUNClass;
import com.ddbj.ld.data.beans.sra.run.RunConverter;
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
public class SraRunService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final MessageModule messageModule;

    private final ObjectMapper objectMapper;

    private final SraRunDao runDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<IndexRequest> get(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var requests = new ArrayList<IndexRequest>();

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_RUN.start;
            var endTag    = XmlTagEnum.SRA_RUN.end;

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

                    // Description に該当するデータは存在しないためrunではnullを設定
                    String description = null;

                    // name 取得
                    var name = properties.getAlias();

                    // sra-run/[DES]RA??????
                    var url = this.jsonModule.getUrl(type, identifier);

                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    var dbXrefs = new ArrayList<DBXrefsBean>();

                    // runはanalysis以外一括で取得できる
                    // bioproject、biosample、submission、experiment、study、sample、status、visibility、date_created、date_modified、date_published
                    var run = this.runDao.select(identifier);

                    if(null != run) {
                        dbXrefs.add(this.jsonModule.getDBXrefs(run.getBioProject(), bioProjectType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(run.getBioSample(), bioSampleType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(run.getSubmission(), submissionType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(run.getExperiment(), experimentType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(run.getStudy(), studyType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(run.getSample(), sampleType));
                    }

                    // status, visibility、日付取得処理
                    var status = null == run ? StatusEnum.LIVE.status : run.getStatus();
                    var visibility = null == run ? VisibilityEnum.PUBLIC.visibility : run.getVisibility();
                    var dateCreated = null == run ? null : this.jsonModule.parseLocalDateTime(run.getReceived());
                    var dateModified = null == run ? null : this.jsonModule.parseLocalDateTime(run.getUpdated());
                    var datePublished = null == run ? null : this.jsonModule.parseLocalDateTime(run.getPublished());

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
            this.messageModule.noticeErrorInfo(TypeEnum.RUN.type, this.errorInfo);

        } else {
            var comment = String.format(
                    "%s\nsra-run validation success.",
                    this.config.message.mention
            );

            this.messageModule.postMessage(this.config.message.channelId, comment);
        }

        this.errorInfo = new HashMap<>();
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
}
