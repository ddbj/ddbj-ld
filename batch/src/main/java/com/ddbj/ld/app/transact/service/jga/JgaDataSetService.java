package com.ddbj.ld.app.transact.service.jga;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.jga.JgaDataSetDataDao;
import com.ddbj.ld.app.transact.dao.jga.JgaDataSetPolicyDao;
import com.ddbj.ld.app.transact.dao.jga.JgaDateDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.OrganismEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.jga.dataset.DATASETClass;
import com.ddbj.ld.data.beans.jga.dataset.DatasetConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class JgaDataSetService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;

    private final JgaDateDao dateDao;
    private final JgaDataSetPolicyDao dataSetPolicyDao;
    private final JgaDataSetDataDao dataSetDataDao;

    public void register() {

        var path = this.config.file.jga.dataSet;

        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb  = null;
            var jsonList      = new ArrayList<JsonBean>();
            var maximumRecord = this.config.other.maximumRecord;

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_DATASET.start;
            var endTag    = XmlTagEnum.JGA_DATASET.end;

            // JSONに使用する項目のうち固定値のもの
            var dacIdentifier = "JGAC000001";
            var dacType       = TypeEnum.JGA_DAC.type;
            var dac = new DBXrefsBean(
                    dacIdentifier,
                    dacType,
                    this.jsonModule.getUrl(TypeEnum.JGA_DAC.type, dacIdentifier)
            );
            var type               = TypeEnum.JGA_DATASET.type;
            var isPartOf           = IsPartOfEnum.JGA.isPartOf;
            var organismName       = OrganismEnum.HOMO_SAPIENS.name;
            var organismIdentifier = OrganismEnum.HOMO_SAPIENS.identifier;
            var organism           = this.jsonModule.getOrganism(organismName, organismIdentifier);

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
                    var description = properties.getDescription();
                    var name = properties.getAlias();
                    var url = this.jsonModule.getUrl(type, identifier);
                    // FIXME SameAsのマッピング(SECONDARY_IDか？
                    List<SameAsBean> sameAs = null;
                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    // DbXrefsのデータを作成
                    // オブジェクト間の関係性を取得する Study (1) → Dataset (n) → Policy (1) → DAC (NBDC, 1)
                    // DatasetからPolicyを取得
                    // Dataset→Data→Experimentと経由してStudyを取得
                    // Dacは1つで固定のためDBからの取得は不要
                    var dbXrefs = new ArrayList<DBXrefsBean>();
                    var studyList  = this.dataSetDataDao.selStudy(identifier);
                    var policyList = this.dataSetPolicyDao.selPolicy(identifier);
                    dbXrefs.addAll(policyList);
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
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    jsonList.add(bean);

                    if(jsonList.size() == maximumRecord) {
                        this.searchModule.bulkInsert(type, jsonList);
                        jsonList.clear();
                    }
                }
            }

            if(jsonList.size() > 0) {
                this.searchModule.bulkInsert(type, jsonList);
            }

        } catch (IOException e) {
            log.error("Not exists file:{}", path, e);
        }
    }

    private DATASETClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = DatasetConverter.fromJsonString(json);

            return bean.getDataset();
        } catch (IOException e) {
            log.error("Converting metadata to bean is failed. xml path: {}, json:{}", path, json, e);

            return null;
        }
    }
}
