package com.ddbj.ld.app.transact.service.dra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.dra.sample.SAMPLEClass;
import com.ddbj.ld.data.beans.dra.sample.SampleConverter;
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
public class DraSampleService {

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
            var startTag  = XmlTagEnum.DRA_SAMPLE.start;
            var endTag    = XmlTagEnum.DRA_SAMPLE.end;

            // 固定値
            // typeの設定
            var type = TypeEnum.SAMPLE.getType();
            // "DRA"固定
            var isPartOf = IsPartOfEnum.DRA.getIsPartOf();
            // sampleのtypeの設定
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
                    var title = properties.getTitle();

                    // Description 取得
                    var description = properties.getDescription();

                    // name 取得
                    String name = properties.getAlias();

                    // dra-sample/[DES]RA??????
                    var url = this.jsonModule.getUrl(type, identifier);

                    // 自分と同値の情報を保持するデータを指定
                    var externalID = properties.getIdentifiers().getExternalID();

                    var dbXrefs = new ArrayList<DBXrefsBean>();

                    List<SameAsBean> sameAs = null;
                    if (externalID != null) {
                        sameAs = new ArrayList<>();

                        var sampleId = externalID.get(0).getContent();
                        var sampleUrl = this.jsonModule.getUrl(type, sampleId);

                        sameAs.add(new SameAsBean(sampleId, sampleType, sampleUrl));
                        dbXrefs.add(new DBXrefsBean(sampleId, sampleType, sampleUrl));
                    }

                    // bioproject, experiment, run, study
                    // TODO select * from t_dra_run where sample = 'DRS000001';

                    // submission
                    // TODO SELECT * FROM t_dra_sample WHERE accession = ?;

                    // 生物名とIDを設定
                    var samplename = properties.getSampleName();
                    var organismName = samplename.getScientificName();
                    var organismIdentifier = samplename.getTaxonID();
                    var organism = this.jsonModule.getOrganism(organismName, organismIdentifier);

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