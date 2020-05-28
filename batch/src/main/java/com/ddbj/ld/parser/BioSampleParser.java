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

import com.ddbj.ld.common.ParserHelper;
import com.ddbj.ld.bean.BioSampleBean;

@Component
@AllArgsConstructor
@Slf4j
public class BioSampleParser {
    private ParserHelper parserHelper;

    // TODO description, dateCreated
    public List<BioSampleBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject bioSampleSet  = XML.toJSONObject(xml).getJSONObject("BioSampleSet");
            Object bioSampleObject   = bioSampleSet.get("BioSample");

            List<BioSampleBean> bioSampleBeanList = new ArrayList<>();

            if(bioSampleObject instanceof JSONArray) {
                JSONArray bioSampleArray = (JSONArray)bioSampleObject;

                for(int n = 0; n < bioSampleArray.length(); n++) {
                    JSONObject bioSample  = bioSampleArray.getJSONObject(n);
                    BioSampleBean bioSampleBean = getBean(bioSample);
                    bioSampleBeanList.add(bioSampleBean);
                }
            } else {
                JSONObject bioSample  = (JSONObject)bioSampleObject;
                BioSampleBean bioSampleBean = getBean(bioSample);
                bioSampleBeanList.add(bioSampleBean);
            }

            return bioSampleBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private BioSampleBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");

        // このdescriptionは文字列ではないため格納しない
        JSONObject description = obj.getJSONObject("Description");

        String name           = description.getString("SampleName");
        String title          = description.getString("Title");
        String properties     = obj.toString();
        String dateModified   = obj.getString("last_update");
        String datePublished  = obj.getString("publication_date");

        BioSampleBean bioSampleBean = new BioSampleBean();
        bioSampleBean.setIdentifier(identifier);
        bioSampleBean.setName(name);
        bioSampleBean.setTitle(title);
        bioSampleBean.setProperties(properties);
        bioSampleBean.setDateModified(dateModified);
        bioSampleBean.setDatePublished(datePublished);

        return bioSampleBean;
    }
}
