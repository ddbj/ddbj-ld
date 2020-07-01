package com.ddbj.ld.parser;

import com.ddbj.ld.bean.JsonBean;
import com.ddbj.ld.common.IsPartOfEnum;
import com.ddbj.ld.common.OrganismEnum;
import com.ddbj.ld.common.ParserHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class JgaParser {
    private ParserHelper parserHelper;

    public List<JsonBean> parse(String xmlFile, String type, String setTag, String targetTag) {
        String isPartOf = IsPartOfEnum.JGA.getIsPartOf();
        String organismName = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
        String organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();

        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject xmlSet  = XML.toJSONObject(xml).getJSONObject(setTag);
            Object xmlObject   = xmlSet.get(targetTag);

            List<JsonBean> xmlBeanList = new ArrayList<>();

            if(xmlObject instanceof JSONArray) {
                JSONArray xmlArray = ((JSONArray)xmlObject);

                for(int i = 0; i < xmlArray.length(); i++) {
                    JSONObject jsonObject  = xmlArray.getJSONObject(i);
                    JsonBean xmlBean = parserHelper.getBean(jsonObject, type, isPartOf, organismName, organismIdentifier);
                    xmlBeanList.add(xmlBean);
                }
            } else {
                JSONObject jsonObject = (JSONObject)xmlObject;
                JsonBean xmlBean = parserHelper.getBean(jsonObject, type, isPartOf, organismName, organismIdentifier);
                xmlBeanList.add(xmlBean);
            }

            return xmlBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }
}
