package com.ddbj.ld.parser;

import com.ddbj.ld.bean.DataSetBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class DataSetParser {
    private ParserHelper parserHelper;

    // TODO name, dateCreated, dateModified, datePublished
    public List<DataSetBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject dataSetSet  = XML.toJSONObject(xml).getJSONObject("DATASET_SET");
            Object dataSetObject   = dataSetSet.get("DATASET");

            List<DataSetBean> dataSetBeanList = new ArrayList<>();

            if(dataSetObject instanceof JSONArray) {
                JSONArray dataSetArray = (JSONArray)dataSetObject;

                for(int i = 0; i < dataSetArray.length(); i++) {
                    JSONObject dataSet  = dataSetArray.getJSONObject(i);
                    DataSetBean dataSetBean = getBean(dataSet);
                    dataSetBeanList.add(dataSetBean);
                }
            } else {
                JSONObject dataSet  = (JSONObject)dataSetObject;
                DataSetBean dataSetBean = getBean(dataSet);
                dataSetBeanList.add(dataSetBean);
            }

            return dataSetBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private DataSetBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");

        String title =
                  obj.has("TITLE")
                ? obj.getString("TITLE")
                : null;

        String description =
                  obj.has("DESCRIPTION")
                ? obj.getString("DESCRIPTION")
                : null;

        String properties  = obj.toString();

        DataSetBean dataSetBean = new DataSetBean();
        dataSetBean.setIdentifier(identifier);
        dataSetBean.setTitle(title);
        dataSetBean.setDescription(description);
        dataSetBean.setProperties(properties);

        return dataSetBean;
    }
}
