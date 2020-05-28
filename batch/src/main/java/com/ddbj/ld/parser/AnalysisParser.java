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

import com.ddbj.ld.bean.AnalysisBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class AnalysisParser {
    private ParserHelper    parserHelper;

    // TODO name 日付系はSRAAccessionsから取得
    public List<AnalysisBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject analysisSet  = XML.toJSONObject(xml).getJSONObject("ANALYSIS_SET");
            Object analysisObject   = analysisSet.get("ANALYSIS");

            List<AnalysisBean> analysisBeanList = new ArrayList<>();

            if(analysisObject instanceof JSONArray) {
                JSONArray analysisArray = ((JSONArray)analysisObject);

                for(int n = 0; n < analysisArray.length(); n++) {
                    JSONObject analysis  = analysisArray.getJSONObject(n);
                    AnalysisBean analysisBean = getBean(analysis);
                    analysisBeanList.add(analysisBean);
                }
            } else {
                JSONObject analysis = (JSONObject)analysisObject;
                AnalysisBean analysisBean = getBean(analysis);
                analysisBeanList.add(analysisBean);
            }

            return analysisBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private AnalysisBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");
        String title       = obj.getString("TITLE");
        String description = obj.getString("DESCRIPTION");
        String properties  = obj.toString();

        AnalysisBean analysisBean = new AnalysisBean();
        analysisBean.setIdentifier(identifier);
        analysisBean.setTitle(title);
        analysisBean.setDescription(description);
        analysisBean.setProperties(properties);

        return analysisBean;
    }
}
