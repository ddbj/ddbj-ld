package com.ddbj.ld.parser.dra;

import com.ddbj.ld.bean.dra.RunBean;
import com.ddbj.ld.common.annotation.Parser;
import com.ddbj.ld.common.helper.ParserHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Parser
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

                for(int i = 0; i < runArray.length(); i++) {
                    JSONObject run  = runArray.getJSONObject(i);
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

        String title =
                  obj.has("TITLE")
                ? obj.getString("TITLE")
                : null;

        String properties    = obj.toString();

        RunBean runBean = new RunBean();
        runBean.setIdentifier(identifier);
        runBean.setTitle(title);
        runBean.setProperties(properties);

        return runBean;
    }
}
