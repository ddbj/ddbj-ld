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

import com.ddbj.ld.bean.SubmissionBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class SubmissionParser {
    private ParserHelper parserHelper;

    // TODO nameとdescription 日付系はSRAAccessionsから取得
    public List<SubmissionBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            Object submissionObject   = XML.toJSONObject(xml).get("SUBMISSION");

            List<SubmissionBean> submissionBeanList = new ArrayList<>();

            if(submissionObject instanceof JSONArray) {
                JSONArray submissionArray = (JSONArray)submissionObject;

                for(int i = 0; i < submissionArray.length(); i++) {
                    JSONObject submission  = submissionArray.getJSONObject(i);
                    SubmissionBean submissionBean = getBean(submission);
                    submissionBeanList.add(submissionBean);
                }
            } else {
                JSONObject submission  = (JSONObject)submissionObject;
                SubmissionBean submissionBean = getBean(submission);
                submissionBeanList.add(submissionBean);
            }

            return submissionBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private SubmissionBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");

        String title =
                  obj.has("TITLE")
                ? obj.getString("TITLE")
                : null;

        String properties = obj.toString();

        SubmissionBean submissionBean = new SubmissionBean();
        submissionBean.setIdentifier(identifier);
        submissionBean.setTitle(title);
        submissionBean.setProperties(properties);

        return submissionBean;
    }
}
