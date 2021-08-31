package com.ddbj.ld.app.transact.service.dra;

import com.ddbj.ld.app.core.module.JsonModule;
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
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DraStudyService {

    private final JsonModule jsonModule;

    private final ObjectMapper objectMapper;

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
            var bioProjectType = TypeEnum.BIOPROJECT.type;

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

                    if (externalid != null) {
                        // 自分と同値(Sample)の情報を保持するデータを指定
                        sameAs = new ArrayList<>();

                        var bioProjectId = externalid.get(0).getContent();
                        var bioProjectUrl = this.jsonModule.getUrl(bioProjectType, bioProjectId);

                        sameAs.add(new SameAsBean(bioProjectId, bioProjectType, bioProjectUrl));
                        dbXrefs.add(new DBXrefsBean(bioProjectId, bioProjectType, bioProjectUrl));
                    }

                    // biosample, experiment, run, sample
                    // TODO SELECT * FROM t_dra_run WHERE study = ?;

                    // submission
                    // TODO SELECT * FROM t_dra_study WHERE accession = ?;

                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    // TODO status, visibility取得処理
                    var status = StatusEnum.LIVE.status;
                    var visibility = VisibilityEnum.PUBLIC.visibility;

                    // TODO 日付取得処理
                    var dateCreated = "";
                    var dateModified = "";
                    var datePublished = "";

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