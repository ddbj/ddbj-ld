package com.ddbj.ld.app.transact.service.jga;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.jga.JgaDataSetPolicyDao;
import com.ddbj.ld.app.transact.dao.jga.JgaDateDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.jga.policy.POLICYClass;
import com.ddbj.ld.data.beans.jga.policy.PolicyConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class JgaPolicyService {

    private final ObjectMapper objectMapper;

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;

    private final JgaDateDao dateDao;
    private final JgaDataSetPolicyDao dataSetPolicyDao;

    public void register() {

        var path = this.config.file.jga.policy;

        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb  = null;
            var requests     = new BulkRequest();
            var maximumRecord = this.config.other.maximumRecord;

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_POLICY.start;
            var endTag    = XmlTagEnum.JGA_POLICY.end;

            // JSONに使用する項目のうち固定値のもの
            var dacIdentifier = "JGAC000001";
            var dacType       = TypeEnum.JGA_DAC.type;
            var dac = new DBXrefsBean(
                    dacIdentifier,
                    dacType,
                    this.jsonModule.getUrl(TypeEnum.JGA_DAC.type, dacIdentifier)
            );
            var type               = TypeEnum.JGA_POLICY.type;
            var isPartOf           = IsPartOfEnum.JGA.isPartOf;
            var organismName       = OrganismEnum.HOMO_SAPIENS.name;
            var organismIdentifier = OrganismEnum.HOMO_SAPIENS.identifier;
            var organism           = this.jsonModule.getOrganism(organismName, organismIdentifier);
            var status = StatusEnum.LIVE.status;
            var visibility = VisibilityEnum.PUBLIC.visibility;

            if(this.searchModule.existsIndex(type)) {
                this.searchModule.deleteIndex(type);
            }

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb        = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                if(line.contains(endTag)) {
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json文字列をバリデーションにかけてから、Beanに変換する
                    var properties = this.getProperties(json, path);

                    if(null == properties) {
                        continue;
                    }

                    // 共通項目以外のJson項目を作る
                    var identifier = properties.getAccession();
                    var title = properties.getTitle();
                    var description = properties.getPolicyText();
                    var name = properties.getAlias();
                    var url = this.jsonModule.getUrl(type, identifier);
                    // FIXME SameAsのマッピング(SECONDARY_IDか？
                    List<SameAsBean> sameAs = null;
                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    // DbXrefsのデータを作成
                    // オブジェクト間の関係性を取得する Study (1) → Dataset (n) → Policy (1) → DAC (NBDC, 1)
                    // PolicyからDataSetを取得
                    // Policy→Dataset→Data→Experimentと経由してStudyを取得
                    // Dacは1つで固定のためDBからの取得は不要
                    var dbXrefs = new ArrayList<DBXrefsBean>();
                    var studyList  = this.dataSetPolicyDao.selStudy(identifier);
                    var datasetList = this.dataSetPolicyDao.selDataSet(identifier);
                    dbXrefs.addAll(datasetList);
                    dbXrefs.addAll(studyList);
                    dbXrefs.add(dac);

                    // 日付のデータを作成
                    var dateInfo = this.dateDao.selJgaDate(identifier);
                    var datePublished = this.jsonModule.parseTimestamp((Timestamp)dateInfo.get("date_published"));
                    var dateCreated   = this.jsonModule.parseTimestamp((Timestamp)dateInfo.get("date_created"));
                    var dateModified  = this.jsonModule.parseTimestamp((Timestamp)dateInfo.get("date_modified"));

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

                    if(requests.numberOfActions() == maximumRecord) {
                        this.searchModule.bulkInsert(requests);
                        requests = new BulkRequest();
                    }
                }
            }

            if(requests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(requests);
            }

        } catch (IOException e) {
            log.error("Not exists file:{}", path, e);
        }
    }

    private POLICYClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = PolicyConverter.fromJsonString(json);

            return bean.getPolicy();
        } catch (IOException e) {
            log.error("Converting metadata to bean is failed. xml path: {}, json:{}", path, json, e);

            return null;
        }
    }
}
