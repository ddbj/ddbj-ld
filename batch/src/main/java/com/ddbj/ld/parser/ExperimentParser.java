package com.ddbj.ld.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

import com.ddbj.ld.bean.ExperimentBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class ExperimentParser {
    private ParserHelper parserHelper;

    // TODO nameとdescription 日付系はSRAAccessionsから取得
    public List<ExperimentBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject experimentSet  = XML.toJSONObject(xml).getJSONObject("EXPERIMENT_SET");
            Object experimentObject   = experimentSet.get("EXPERIMENT");

            List<ExperimentBean> experimentBeanList = new ArrayList<>();

            if(experimentObject instanceof JSONArray) {
                JSONArray experimentArray = (JSONArray)experimentObject;

                for(int i = 0; i < experimentArray.length(); i++) {
                    JSONObject experiment  = experimentArray.getJSONObject(i);
                    ExperimentBean experimentBean = getBean(experiment);
                    experimentBeanList.add(experimentBean);
                }
            } else {
                JSONObject experiment  = (JSONObject)experimentObject;
                ExperimentBean experimentBean = getBean(experiment);
                experimentBeanList.add(experimentBean);
            }

            return experimentBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private ExperimentBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");

        String title =
                  obj.has("TITLE")
                ? obj.getString("TITLE")
                : null;

        String properties    = obj.toString();

        ExperimentBean experimentBean = new ExperimentBean();
        experimentBean.setIdentifier(identifier);
        experimentBean.setTitle(title);
        experimentBean.setProperties(properties);

        return experimentBean;
    }
}
