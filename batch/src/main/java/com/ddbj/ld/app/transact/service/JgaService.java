package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.transact.dao.jga.DateDao;
import com.ddbj.ld.app.transact.dao.jga.JgaRelationDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.OrganismEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.jga.dac.DACClass;
import com.ddbj.ld.data.beans.jga.dac.DacConverter;
import com.ddbj.ld.data.beans.jga.dataset.DATASETClass;
import com.ddbj.ld.data.beans.jga.dataset.DatasetConverter;
import com.ddbj.ld.data.beans.jga.policy.POLICYClass;
import com.ddbj.ld.data.beans.jga.policy.PolicyConverter;
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

// TODO
//  - status, visibilityを追加する
//  - dbXRefsの取得方法を変更する
@Service
@AllArgsConstructor
@Slf4j
public class JgaService {

    private final JgaRelationDao jgaRelationDao;
    private final JsonModule jsonModule;
    private final DateDao dateDao;

    public List<JsonBean> getStudy(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath))) {
            String line;
            StringBuilder sb = null;
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_STUDY_START.getItem();
            var endTag    = XmlTagEnum.JGA_STUDY_END.getItem();

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
                    var properties = this.getStudyProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    var identifier = properties.getAccession();

                    var descriptor = properties.getDescriptor();

                    var title = descriptor.getStudyTitle();
                    var description = descriptor.getStudyAbstract();
                    // FIXME nameのマッピング
                    String name = null;

                    var type = TypeEnum.JGA_STUDY.getType();

                    var url = this.jsonModule.getUrl(type, identifier);

                    // FIXME SameAsのマッピング(SECONDARY_IDか？
                    List<SameAsBean> sameAs = null;

                    var isPartOf = IsPartOfEnum.JGA.getIsPartOf();

                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();

                    var organism     = this.jsonModule.getOrganism(organismName, organismIdentifier);
                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    List<DBXrefsBean> dbXrefs = new ArrayList<>();

                    // オブジェクト間の関係性を取得する Study (1) → Dataset (n) → Policy (1) → DAC (NBDC, 1)
                    // ExperimentとDataを経由してDatasetのレコードを取得
                    // Analysisを経由してDatasetのレコードを取得
                    // DatasetからPolicyを取得
                    // PolicyからDacを取得
                    List<DBXrefsBean> datasetList = this.jgaRelationDao.selDatasetBelongsToStudy(identifier);

                    if(null != datasetList && 0 < datasetList.size()) {
                        dbXrefs.addAll(datasetList);
                    }

                    for(var dataset: datasetList) {
                        var policyList = this.jgaRelationDao.selSelfAndParentType(dataset.getIdentifier(), TypeEnum.JGA_POLICY.getType());

                        if(null != policyList && policyList.size() > 0) {
                            dbXrefs.addAll(policyList);

                            // dataset - policy間はn対1のため、1つ取得できたら離脱
                            // FIXME JGAD000004のように複数のPolicyを持ってるDatasetもあるようだ、要確認
                            break;
                        }
                    }

                    List<DBXrefsBean> dacList  = this.jgaRelationDao.selDAC();

                    if(null != dacList && dacList.size() > 0) {
                        dbXrefs.addAll(dacList);
                    }

                    var dateInfo = this.dateDao.selJgaDate(identifier);

                    if(null == dateInfo) {
                        log.warn("Date information is nothing. Skip this record. accession:" + identifier);
                        return null;
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
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    public List<JsonBean> getDataset(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath))) {
            String line;
            StringBuilder sb = null;
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_DATASET_START.getItem();
            var endTag    = XmlTagEnum.JGA_DATASET_END.getItem();

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
                    var properties = this.getDatasetProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    var identifier = properties.getAccession();

                    var title = properties.getTitle();
                    var description = properties.getDescription();
                    // FIXME nameのマッピング
                    String name = null;

                    var type = TypeEnum.JGA_DATASET.getType();

                    var url = this.jsonModule.getUrl(type, identifier);

                    // FIXME sameAsのマッピング（alias？
                    List<SameAsBean> sameAs = null;

                    var isPartOf = IsPartOfEnum.JGA.getIsPartOf();

                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();

                    var organism     = this.jsonModule.getOrganism(organismName, organismIdentifier);
                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    List<DBXrefsBean> dbXrefs = new ArrayList<>();

                    var studyList = this.jgaRelationDao.selSelfAndParentType(identifier, TypeEnum.JGA_STUDY.getType());

                    if(null != studyList && studyList.size() > 0) {
                        dbXrefs.addAll(studyList);
                    }

                    var policyList = this.jgaRelationDao.selSelfAndParentType(identifier, TypeEnum.JGA_POLICY.getType());

                    if(null != policyList && policyList.size() > 0) {
                        dbXrefs.addAll(policyList);
                    }

                    var dacList  = this.jgaRelationDao.selDAC();

                    if(null != dacList && dacList.size() > 0) {
                        dbXrefs.addAll(dacList);
                    }

                    var dateInfo = this.dateDao.selJgaDate(identifier);

                    if(null == dateInfo) {
                        log.warn("Date information is nothing. Skip this record. accession:" + identifier);
                        return null;
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
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    public List<JsonBean> getPolicy(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath))) {
            String line;
            StringBuilder sb = null;
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_POLICY_START.getItem();
            var endTag    = XmlTagEnum.JGA_POLICY_END.getItem();

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
                    var properties = this.getPolicyProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    var identifier  = properties.getAccession();

                    var title       = properties.getTitle();
                    var description = properties.getPolicyText();
                    // FIXME nameのマッピング
                    String name = null;

                    var type = TypeEnum.JGA_POLICY.getType();

                    var url = this.jsonModule.getUrl(type, identifier);

                    // FIXME sameAsのマッピング（SECONDARY_ID？
                    List<SameAsBean> sameAs = null;

                    var isPartOf = IsPartOfEnum.JGA.getIsPartOf();

                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();

                    var organism     = this.jsonModule.getOrganism(organismName, organismIdentifier);
                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    List<DBXrefsBean> dbXrefs = new ArrayList<>();

                    var datasetList = this.jgaRelationDao.selParentAndSelfType(identifier, TypeEnum.JGA_DATASET.getType());

                    if(null != datasetList && datasetList.size() > 0) {
                        dbXrefs.addAll(datasetList);
                    }

                    var studyList = this.jgaRelationDao.selStudyBelongsToPolicy(identifier);

                    if(null != studyList && studyList.size() > 0) {
                        dbXrefs.addAll(studyList);
                    }

                    var dacList  = this.jgaRelationDao.selDAC();

                    if(null != dacList && dacList.size() > 0) {
                        dbXrefs.addAll(dacList);
                    }

                    var dateInfo = this.dateDao.selJgaDate(identifier);

                    if(null == dateInfo) {
                        log.warn("Date information is nothing. Skip this record. accession:" + identifier);
                        return null;
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
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    public List<JsonBean> getDAC(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath))) {
            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_DAC_START.getItem();
            var endTag    = XmlTagEnum.JGA_DAC_END.getItem();

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
                    var properties = this.getDacProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    var identifier = properties.getAccession();

                    var title       = properties.getTitle();
                    // FIXME descriptionのマッピング
                    String description = null;
                    // FIXME nameのマッピング
                    String name = null;

                    var type = TypeEnum.JGA_DAC.getType();

                    var url = this.jsonModule.getUrl(type, identifier);

                    // FIXME sameAsのマッピング
                    List<SameAsBean> sameAs = null;

                    var isPartOf = IsPartOfEnum.JGA.getIsPartOf();

                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();

                    var organism     = this.jsonModule.getOrganism(organismName, organismIdentifier);
                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    List<DBXrefsBean> dbXrefs = new ArrayList<>();

                    // DACは一種類しかなく、他オブジェクトが一つのDACに所属しているため、DBからそのまま取得する
                    var studyList   = this.jgaRelationDao.selDistinctParentAndParentType(TypeEnum.JGA_STUDY.getType());
                    var datasetList = this.jgaRelationDao.selDistinctSelfAndSelfType(TypeEnum.JGA_DATASET.getType());
                    var policyList  = this.jgaRelationDao.selParentAndSelfType(identifier, TypeEnum.JGA_POLICY.getType());

                    if(null != studyList && studyList.size() > 0) {
                        dbXrefs.addAll(studyList);
                    }

                    if(null != datasetList && datasetList.size() > 0) {
                        dbXrefs.addAll(datasetList);
                    }

                    if(null != policyList && policyList.size() > 0) {
                        dbXrefs.addAll(policyList);
                    }

                    var dateInfo = this.dateDao.selJgaDate(identifier);

                    if(null == dateInfo) {
                        log.warn("Date information is nothing. Skip this record. accession:" + identifier);
                        return null;
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
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    private STUDYClass getStudyProperties(
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

    private DATASETClass getDatasetProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            var bean = DatasetConverter.fromJsonString(json);

            return bean.getDataset();
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }

    private POLICYClass getPolicyProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            var bean = PolicyConverter.fromJsonString(json);

            return bean.getPolicy();
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }

    private DACClass getDacProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            var bean = DacConverter.fromJsonString(json);

            return bean.getDAC();
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }
}
