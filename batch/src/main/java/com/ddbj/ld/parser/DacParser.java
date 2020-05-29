package com.ddbj.ld.parser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ddbj.ld.bean.DacBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class DacParser {
    private ParserHelper parserHelper;

    // TODO name, title, description, dateCreated, dateModified, datePublished
    public List<DacBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject dacSet  = XML.toJSONObject(xml).getJSONObject("DAC_SET");
            Object dacObject   = dacSet.get("DAC");

            List<DacBean> dacBeanList = new ArrayList<>();

            if(dacObject instanceof JSONArray) {
                JSONArray dacArray = (JSONArray)dacObject;

                for(int i = 0; i < dacArray.length(); i++) {
                    JSONObject dac  = dacArray.getJSONObject(i);
                    DacBean dacBean = getBean(dac);
                    dacBeanList.add(dacBean);
                }
            } else {
                JSONObject dac  = (JSONObject)dacObject;
                DacBean dacBean = getBean(dac);
                dacBeanList.add(dacBean);
            }

            return dacBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private DacBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");
        String properties  = obj.toString();

        DacBean dacBean = new DacBean();
        dacBean.setIdentifier(identifier);
        dacBean.setProperties(properties);

        return dacBean;
    }
}
