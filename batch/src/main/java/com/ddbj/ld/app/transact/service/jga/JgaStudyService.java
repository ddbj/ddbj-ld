package com.ddbj.ld.app.transact.service.jga;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.jga.DateDao;
import com.ddbj.ld.app.transact.dao.jga.ExperimentStudyDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.OrganismEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.jga.study.STUDYClass;
import com.ddbj.ld.data.beans.jga.study.StudyConverter;
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
public class JgaStudyService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;

    private final DateDao dateDao;
    private final ExperimentStudyDao experimentStudyDao;

    public void register() {

        var path = this.config.file.jga.study;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var jsonList = new ArrayList<JsonBean>();

            var isStarted = false;
            // FIXME Enumの変数を増やし、コンストラクタを減らす
            var startTag  = XmlTagEnum.JGA_STUDY_START.item;
            var endTag    = XmlTagEnum.JGA_STUDY_END.item;

            // FIXME DACのこのあたりも定数化したい
            var dacIdentifier = "JGAC000001";
            var dacType = TypeEnum.JGA_DAC.type;

            var dac = new DBXrefsBean(
                    dacIdentifier,
                    dacType,
                    this.jsonModule.getUrl(TypeEnum.JGA_DAC.type, dacIdentifier)
            );

            var type = TypeEnum.JGA_STUDY.type;

            if(this.searchModule.existsIndex(type)) {
                this.searchModule.deleteIndex(type);
            }

            var maximumRecord = this.config.other.maximumRecord;

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

                    // Json文字列をバリデーションにかけてから、Beanに変換する
                    var properties = this.getProperties(json, path);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    var identifier = properties.getAccession();
                    var descriptor = properties.getDescriptor();
                    var title = descriptor.getStudyTitle();
                    var description = descriptor.getStudyAbstract();
                    var name = properties.getAlias();
                    var url = this.jsonModule.getUrl(type, identifier);

                    // FIXME SameAsのマッピング(SECONDARY_IDか？
                    List<SameAsBean> sameAs = null;

                    var isPartOf = IsPartOfEnum.JGA.isPartOf;

                    // FIXME Enumの変数を増やし、コンストラクタを減らす
                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.item;
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.item;
                    var organism     = this.jsonModule.getOrganism(organismName, organismIdentifier);
                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    var dbXrefs = new ArrayList<DBXrefsBean>();

                    // オブジェクト間の関係性を取得する Study (1) → Dataset (n) → Policy (1) → DAC (NBDC, 1)
                    // ExperimentとDataを経由してDatasetのレコードを取得
                    // Analysisを経由してDatasetのレコードを取得
                    // DatasetからPolicyを取得
                    // PolicyからDacを取得(Dacは1つで固定のためDBからの取得は不要
                    var datasetList = this.experimentStudyDao.selDataSet(identifier);
                    var policyList  = this.experimentStudyDao.selPolicy(identifier);

                    dbXrefs.addAll(datasetList);
                    dbXrefs.addAll(policyList);
                    dbXrefs.add(dac);

                    var dateInfo = this.dateDao.selJgaDate(identifier);

                    if(null == dateInfo) {
                        log.warn("Date information is nothing. Skip this record. accession:" + identifier);
                    }

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

    private STUDYClass getProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            var bean = StudyConverter.fromJsonString(json);

            return bean.getStudy();
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }
}
