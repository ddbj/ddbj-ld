package com.ddbj.ld.parser.jga;

import com.ddbj.ld.bean.common.DBXrefsBean;
import com.ddbj.ld.bean.common.DistributionBean;
import com.ddbj.ld.bean.common.JsonBean;
import com.ddbj.ld.bean.common.OrganismBean;
import com.ddbj.ld.common.constant.IsPartOfEnum;
import com.ddbj.ld.common.constant.OrganismEnum;
import com.ddbj.ld.common.constant.TypeEnum;
import com.ddbj.ld.common.helper.DateHelper;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.dao.jga.JgaDateDao;
import com.ddbj.ld.dao.jga.JgaRelationDao;
import com.ddbj.ld.parser.common.JsonParser;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class JgaParser {
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
                    JsonBean bean          = getBean(jsonObject, type, isPartOf, organismName, organismIdentifier);

                    if(ObjectUtils.isEmpty(bean)) {
                        continue;
                    }

                    jsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
                }
            }

            if(xmlObject instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject)xmlObject;
                JsonBean bean         = getBean(jsonObject, type, isPartOf, organismName, organismIdentifier);

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

        // それぞれのメタデータ独自のもの
        String title = null;
        String description = null;

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

                break;
            case DAC:
            default:
                break;
        }

        jsonBean.setTitle(title);
        jsonBean.setDescription(description);

        List<DBXrefsBean> selfDBXrefsBeanList   = jgaRelationDao.selSelf(identifier);
        List<DBXrefsBean> parentDBXrefsBeanList = jgaRelationDao.selParent(identifier);

        selfDBXrefsBeanList.addAll(parentDBXrefsBeanList);
        jsonBean.setDbXrefs(selfDBXrefsBeanList);

        Map<String, Object> jgaDate = jgaDateDao.selJgaDate(identifier);

        if(jgaDate.size() == 0) {
            return null;
        }

        String datePublished = dateHelper.parse((Timestamp)jgaDate.get("date_published"));

        if(ObjectUtils.isEmpty(datePublished)) {
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
