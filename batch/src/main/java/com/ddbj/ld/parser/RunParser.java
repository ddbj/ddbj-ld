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

import com.ddbj.ld.bean.RunBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class RunParser {
    private ParserHelper parserHelper;

    // TODO nameとdescription
    public List<RunBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject runSet  = XML.toJSONObject(xml).getJSONObject("RUN_SET");
            Object runObject   = runSet.get("RUN");

            List<RunBean> runBeanList = new ArrayList<>();

            if(runObject instanceof JSONArray) {
                JSONArray runArray = (JSONArray)runObject;

                for(int n = 0; n < runArray.length(); n++) {
                    JSONObject run  = runArray.getJSONObject(n);
                    RunBean runBean = getBean(run);
                    runBeanList.add(runBean);
                }
            } else {
                JSONObject run  = (JSONObject)runObject;
                RunBean runBean = getBean(run);
                runBeanList.add(runBean);
            }

            return runBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private RunBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");
        String title         = obj.getString("TITLE");
        String properties    = obj.toString();

        RunBean runBean = new RunBean();
        runBean.setIdentifier(identifier);
        runBean.setTitle(title);
        runBean.setProperties(properties);

        return runBean;
    }
}
