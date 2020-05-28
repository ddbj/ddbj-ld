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

import com.ddbj.ld.bean.SampleBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class SampleParser {
    private ParserHelper parserHelper;

    // TODO name 日付系はSRAAccessionsから取得
    public List<SampleBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject sampleSet  = XML.toJSONObject(xml).getJSONObject("SAMPLE_SET");
            Object sampleObject   = sampleSet.get("SAMPLE");

            List<SampleBean> sampleBeanList = new ArrayList<>();

            if(sampleObject instanceof JSONArray) {
                JSONArray sampleArray = ((JSONArray)sampleObject);

                for(int n = 0; n < sampleArray.length(); n++) {
                    JSONObject sample  = sampleArray.getJSONObject(n);
                    SampleBean sampleBean = getBean(sample);
                    sampleBeanList.add(sampleBean);
                }
            } else {
                JSONObject sample = (JSONObject)sampleObject;
                SampleBean sampleBean = getBean(sample);
                sampleBeanList.add(sampleBean);
            }

            return sampleBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private SampleBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");
        String title       = obj.getString("TITLE");
        String description = obj.getString("DESCRIPTION");
        String properties  = obj.toString();

        SampleBean sampleBean = new SampleBean();
        sampleBean.setIdentifier(identifier);
        sampleBean.setTitle(title);
        sampleBean.setDescription(description);
        sampleBean.setProperties(properties);

        return sampleBean;
    }
}
