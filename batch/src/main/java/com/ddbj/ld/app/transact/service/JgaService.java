package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.core.parser.common.JsonParser;
import com.ddbj.ld.app.transact.dao.jga.JgaDateDao;
import com.ddbj.ld.app.transact.dao.jga.JgaRelationDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.OrganismEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.helper.DateHelper;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.DistributionBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.OrganismBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class JgaService {
    private final ParserHelper parserHelper;
    private final DateHelper dateHelper;
    private UrlHelper urlHelper;
    private final JgaRelationDao jgaRelationDao;
    private final JgaDateDao jgaDateDao;
    private final JsonParser jsonParser;

    public Map<String, String> parse(String xmlFile, String type, String setTag, String targetTag) {
        String isPartOf           = IsPartOfEnum.JGA.getIsPartOf();
        String organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
        String organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();

        try {
            String xml         = parserHelper.readAll(xmlFile);
            JSONObject xmlSet  = XML.toJSONObject(xml).getJSONObject(setTag);
            Object xmlObject   = xmlSet.get(targetTag);

            Map<String, String> jsonMap = new HashMap<>();
            ObjectMapper mapper = jsonParser.getMapper();

            if(xmlObject instanceof JSONArray) {
                JSONArray xmlArray = (JSONArray)xmlObject;

                for(int i = 0; i < xmlArray.length(); i++) {
                    JSONObject jsonObject  = xmlArray.getJSONObject(i);
                    JsonBean bean          = this.getBean(jsonObject, type, isPartOf, organismName, organismIdentifier);

                    if(ObjectUtils.isEmpty(bean)) {
                        continue;
                    }

                    jsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
                }
            }

            if(xmlObject instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject)xmlObject;
                JsonBean bean         = this.getBean(jsonObject, type, isPartOf, organismName, organismIdentifier);

                if(ObjectUtils.isEmpty(bean)) {
                    return null;
                }

                jsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
            }

            return jsonMap;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private JsonBean getBean(JSONObject obj, String type, String isPartOf, String organismName, String organismIdentifier) {
        String identifier  = obj.getString("accession");
        String url         = urlHelper.getUrl(type, identifier);
        String properties  = obj.toString();

        OrganismBean organism               = parserHelper.getOrganism(organismName, organismIdentifier);
        List<DistributionBean> distribution = parserHelper.getDistribution(type, identifier);

        // 共通部分
        JsonBean jsonBean = new JsonBean();
        jsonBean.setIdentifier(identifier);
        jsonBean.setType(type);
        jsonBean.setUrl(url);
        jsonBean.setIsPartOf(isPartOf);
        jsonBean.setOrganism(organism);
        jsonBean.setDistribution(distribution);
        jsonBean.setProperties(properties);

        // タイトルとディスクリプションはそれぞれのメタデータ独自のタグを当てはめる
        String title       = null;
        String description = null;

        List<DBXrefsBean> dBXrefs = new ArrayList<>();

        List<DBXrefsBean> studyList      = new ArrayList<>();
        List<DBXrefsBean> experimentList = new ArrayList<>();
        List<DBXrefsBean> analysisList   = new ArrayList<>();
        List<DBXrefsBean> dataList       = new ArrayList<>();
        List<DBXrefsBean> datasetList    = new ArrayList<>();
        List<DBXrefsBean> policyList     = new ArrayList<>();
        List<DBXrefsBean> dacList        = new ArrayList<>();

        // 一時格納用
        List<DBXrefsBean> recordList = new ArrayList<>();

        // 重複回避用
        Map<String, DBXrefsBean> studyMap;
        Map<String, DBXrefsBean> datasetMap;
        Map<String, DBXrefsBean> policyMap;
        Map<String, DBXrefsBean> dacMap;

        TypeEnum typeEnum = TypeEnum.getTypeByArgs(type);

        switch(typeEnum) {
            case JGA_STUDY:
                if(obj.has("DESCRIPTOR")) {
                    JSONObject descriptor = obj.getJSONObject("DESCRIPTOR");

                    title =
                            descriptor.has("STUDY_TITLE")
                                    ? descriptor.getString("STUDY_TITLE")
                                    : null;

                    description =
                            descriptor.has("STUDY_ABSTRACT")
                                    ? descriptor.getString("STUDY_ABSTRACT")
                                    : null;
                }

                // オブジェクト間の関係性を取得する Study (1) → Dataset (n) → Policy (1) → DAC (NBDC, 1)
                // ExperimentとDataを経由してDatasetのレコードを取得
                // Analysisを経由してDatasetのレコードを取得
                // DatasetからPolicyを取得
                // PolicyからDacを取得
                experimentList = this.jgaRelationDao.selParentAndSelfType(identifier, TypeEnum.JGA_EXPERIMENT.getType());
                dataList       = new ArrayList<>();

                for(var experiment: experimentList) {
                    recordList = this.jgaRelationDao.selParentAndSelfType(experiment.getIdentifier(), TypeEnum.DATA.getType());

                    if(null != recordList && recordList.size() > 0) {
                        dataList.addAll(recordList);
                    }
                }

                // 重複を避けるため一旦Map化
                datasetMap  = new HashMap<>();

                for(var data: dataList) {
                    List<DBXrefsBean> records = this.jgaRelationDao.selParentAndSelfType(data.getIdentifier(), TypeEnum.DATASET.getType());

                    if(null != records && records.size() > 0) {
                        records.forEach(record -> {
                            datasetMap.put(record.getIdentifier(), record);
                        });
                    }
                }

                analysisList = this.jgaRelationDao.selParentAndSelfType(identifier, TypeEnum.JGA_ANALYSIS.getType());

                for(var analysis: analysisList) {
                    recordList = this.jgaRelationDao.selParentAndSelfType(analysis.getIdentifier(), TypeEnum.DATASET.getType());

                    if(null != recordList && recordList.size() > 0) {
                        recordList.forEach(record -> {
                            datasetMap.put(record.getIdentifier(), record);
                        });
                    }
                }

                // Mapをリスト化
                datasetList = new ArrayList<>(datasetMap.values());

                if(null != datasetList && datasetList.size() > 0) {
                    dBXrefs.addAll(datasetList);
                }

                // 重複を避けるため一旦Map化
                policyMap  = new HashMap<>();

                for(var dataset: datasetList) {
                    recordList = this.jgaRelationDao.selSelfAndParentType(dataset.getIdentifier(), TypeEnum.POLICY.getType());

                    if(null != recordList && recordList.size() > 0) {
                        recordList.forEach(record -> {
                            policyMap.put(record.getIdentifier(), record);
                        });
                    }
                }

                // Mapをリスト化
                policyList  = new ArrayList<>(policyMap.values());

                if(null != policyList && policyList.size() > 0) {
                    dBXrefs.addAll(policyList);
                }

                // 重複を避けるため一旦Map化
                dacMap = new HashMap<>();

                for(var policy: policyList) {
                    recordList = this.jgaRelationDao.selSelfAndParentType(policy.getIdentifier(), TypeEnum.DAC.getType());

                    if(null != recordList && recordList.size() > 0) {
                        recordList.forEach(record -> {
                            dacMap.put(record.getIdentifier(), record);
                        });
                    }
                }

                dacList  = new ArrayList<>(dacMap.values());

                if(null != dacList && dacList.size() > 0) {
                    dBXrefs.addAll(dacList);
                }

                break;

            case DATASET:
                title =
                        obj.has("TITLE")
                                ? obj.getString("TITLE")
                                : null;

                description =
                        obj.has("DESCRIPTION")
                                ? obj.getString("DESCRIPTION")
                                : null;

                analysisList = this.jgaRelationDao.selSelfAndParentType(identifier, TypeEnum.JGA_ANALYSIS.getType());

                if(null != analysisList && analysisList.size() > 0) {
                    var analysis = analysisList.get(0);

                    studyList = this.jgaRelationDao.selSelfAndParentType(analysis.getIdentifier(), TypeEnum.JGA_STUDY.getType());
                }

                if(null == studyList || studyList.size() == 0) {
                    // analysis経由でStudyを弾いてこれない場合はdata→experiment経由で弾いてくるのを試みる
                    dataList = this.jgaRelationDao.selSelfAndParentType(identifier, TypeEnum.DATA.getType());

                    if(null != dataList && dataList.size() > 0) {
                        var data = dataList.get(0);

                        experimentList = this.jgaRelationDao.selSelfAndParentType(data.getIdentifier(), TypeEnum.JGA_EXPERIMENT.getType());
                    }

                    if(null != experimentList && experimentList.size() > 0) {
                        var experiment = experimentList.get(0);

                        studyList = this.jgaRelationDao.selSelfAndParentType(experiment.getIdentifier(), TypeEnum.JGA_STUDY.getType());
                    }
                }

                if(null != studyList && studyList.size() > 0) {
                    dBXrefs.addAll(studyList);
                }

                policyList = this.jgaRelationDao.selSelfAndParentType(identifier, TypeEnum.POLICY.getType());

                if(null != policyList && policyList.size() > 0) {
                    dBXrefs.addAll(policyList);
                    dacList = this.jgaRelationDao.selSelfAndParentType(policyList.get(0).getIdentifier(), TypeEnum.DAC.getType());
                }

                if(null != dacList && dacList.size() > 0) {
                    dBXrefs.addAll(dacList);
                }

                break;

            case POLICY:
                title =
                        obj.has("TITLE")
                                ? obj.getString("TITLE")
                                : null;

                description =
                        obj.has("DESCRIPTION")
                                ? obj.getString("POLICY_TEXT")
                                : null;

                datasetList = this.jgaRelationDao.selParentAndSelfType(identifier, TypeEnum.DATASET.getType());

                if(null != datasetList && datasetList.size() > 0) {
                    analysisList = this.jgaRelationDao.selSelfAndParentType(datasetList.get(0).getIdentifier(), TypeEnum.JGA_ANALYSIS.getType());

                    if(null != analysisList && analysisList.size() > 0) {
                        studyList = this.jgaRelationDao.selSelfAndParentType(analysisList.get(0).getIdentifier(), TypeEnum.JGA_STUDY.getType());
                    }

                    if(null == studyList || studyList.size() == 0) {
                        // analysis経由でStudyを弾いてこれない場合はdata→experiment経由で弾いてくるのを試みる
                        dataList = this.jgaRelationDao.selSelfAndParentType(datasetList.get(0).getIdentifier(), TypeEnum.DATA.getType());

                        if(null != dataList && dataList.size() > 0) {
                            experimentList = this.jgaRelationDao.selSelfAndParentType(dataList.get(0).getIdentifier(), TypeEnum.JGA_EXPERIMENT.getType());
                        }

                        if(null != experimentList && experimentList.size() > 0) {
                            studyList = this.jgaRelationDao.selSelfAndParentType(experimentList.get(0).getIdentifier(), TypeEnum.JGA_STUDY.getType());
                        }
                    }

                    if(null != studyList && studyList.size() > 0) {
                        dBXrefs.addAll(studyList);
                    }

                    dBXrefs.addAll(datasetList);
                }

                dacList = this.jgaRelationDao.selSelfAndParentType(identifier, TypeEnum.DAC.getType());

                if(null != dacList && dacList.size() > 0) {
                    dBXrefs.addAll(dacList);
                }

                break;

            case DAC:
                // DACは一種類しかなく、他オブジェクトが一つのDACに所属しているため、DBからそのまま取得する
                studyList = this.jgaRelationDao.selDistinctParentAndParentType(TypeEnum.JGA_STUDY.getType());
                datasetList = this.jgaRelationDao.selDistinctSelfAndSelfType(TypeEnum.DATASET.getType());
                policyList = this.jgaRelationDao.selParentAndSelfType(identifier, TypeEnum.POLICY.getType());

                if(null != studyList && studyList.size() > 0) {
                    dBXrefs.addAll(studyList);
                }

                if(null != datasetList && datasetList.size() > 0) {
                    dBXrefs.addAll(datasetList);
                }

                if(null != policyList && policyList.size() > 0) {
                    dBXrefs.addAll(policyList);
                }

                break;
            default:
                break;
        }

        jsonBean.setTitle(title);
        jsonBean.setDescription(description);
        jsonBean.setDbXrefs(dBXrefs);

        Map<String, Object> jgaDate = jgaDateDao.selJgaDate(identifier);

        if(jgaDate.size() == 0) {
            log.warn("jgaData is nothing. Skip this record. accession:" + identifier);
            return null;
        }

        String datePublished = dateHelper.parse((Timestamp)jgaDate.get("date_published"));

        if(ObjectUtils.isEmpty(datePublished)) {
            log.warn("datePublished is nothing. Skip this record. accession:" + identifier);
            return null;
        }

        String dateCreated  = dateHelper.parse((Timestamp)jgaDate.get("date_created"));
        String dateModified = dateHelper.parse((Timestamp)jgaDate.get("date_modified"));

        jsonBean.setDateCreated(dateCreated);
        jsonBean.setDateModified(dateModified);
        jsonBean.setDatePublished(datePublished);

        return jsonBean;
    }
}
