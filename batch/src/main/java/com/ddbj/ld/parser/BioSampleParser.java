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

        JSONObject description;
        String name  = null;
        String title = null;

        // このdescriptionは文字列ではないため格納しない
        if(obj.has("Description")) {
            description = obj.getJSONObject("Description");

            name =
                  description.has("SampleName")
                ? description.getString("SampleName")
                : null;

            title =
                  description.has("Title")
                ? description.getString("Title")
                : null;
        }

        String properties = obj.toString();

        String dateModified =
              obj.has("last_update")
            ? obj.getString("last_update")
            : null;

        String datePublished =
              obj.has("publication_date")
            ? obj.getString("publication_date")
            : null;

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
