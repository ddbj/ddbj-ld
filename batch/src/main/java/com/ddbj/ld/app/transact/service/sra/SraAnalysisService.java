package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.transact.dao.sra.SraAnalysisDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.analysis.ANALYSISClass;
import com.ddbj.ld.data.beans.sra.analysis.AnalysisConverter;
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
public class SraAnalysisService {

    private final JsonModule jsonModule;

    private final ObjectMapper objectMapper;

    private final SraAnalysisDao analysisDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<IndexRequest> get(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var requests = new ArrayList<IndexRequest>();

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_ANALYSIS.start;
            var endTag    = XmlTagEnum.SRA_ANALYSIS.end;

            // 固定値
            // typeの設定
            var type = TypeEnum.ANALYSIS.getType();
            // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
            List<SameAsBean> sameAs = null;
            // "SRA"固定
            var isPartOf = IsPartOfEnum.SRA.getIsPartOf();
            // 生物名とIDはSampleのみの情報であるため空情報を設定
            OrganismBean organism = null;

            // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
            var bioProjectType = TypeEnum.BIOPROJECT.type;
            var submissionType = TypeEnum.SUBMISSION.type;
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
                    var name = properties.getAlias();

                    // sra-analysis/[DES]RA??????
                    var url = this.jsonModule.getUrl(type, identifier);

                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    var dbXrefs = new ArrayList<DBXrefsBean>();

                    // bioproject、submission、study、status、visibility、date_created、date_modified、date_published
                    var analysis = this.analysisDao.select(identifier);

                    if(null != analysis) {
                        dbXrefs.add(this.jsonModule.getDBXrefs(analysis.getBioProject(), bioProjectType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(analysis.getSubmission(), submissionType));
                        dbXrefs.add(this.jsonModule.getDBXrefs(analysis.getStudy(), studyType));
                    }

                    // status, visibility、日付取得処理
                    var status = null == analysis ? StatusEnum.LIVE.status : analysis.getStatus();
                    var visibility = null == analysis ? VisibilityEnum.PUBLIC.visibility : analysis.getVisibility();
                    var dateCreated = null == analysis ? null : this.jsonModule.parseLocalDateTime(analysis.getReceived());
                    var dateModified = null == analysis ? null : this.jsonModule.parseLocalDateTime(analysis.getUpdated());
                    var datePublished = null == analysis ? null : this.jsonModule.parseLocalDateTime(analysis.getPublished());

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

    private ANALYSISClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = AnalysisConverter.fromJsonString(json);

            return bean.getAnalysis();
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
