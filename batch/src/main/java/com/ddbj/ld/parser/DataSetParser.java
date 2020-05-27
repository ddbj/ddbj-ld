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

import com.ddbj.ld.bean.DataSetBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class DataSetParser {
    private ParserHelper parserHelper;

    // TODO name
    public List<DataSetBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject dataSetSet  = XML.toJSONObject(xml);
            JSONArray dataSetArray = dataSetSet.getJSONObject("DATASET_SET").getJSONArray("DATASET");

            List<DataSetBean> dataSetBeanList = new ArrayList<>();

            for(int n = 0; n < dataSetArray.length(); n++) {
                JSONObject dataSet    = dataSetArray.getJSONObject(n);

                String properties  = dataSet.toString();
                String identifier  = dataSet.getString("accession");
                String title       = dataSet.getString("TITLE");
                String description = dataSet.getString("DESCRIPTION");

                DataSetBean dataSetBean = new DataSetBean();
                dataSetBean.setProperties(properties);
                dataSetBean.setIdentifier(identifier);
                dataSetBean.setTitle(title);
                dataSetBean.setDescription(description);

                dataSetBeanList.add(dataSetBean);
            }

            return dataSetBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }
}
