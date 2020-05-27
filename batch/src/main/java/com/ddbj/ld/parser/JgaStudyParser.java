package com.ddbj.ld.parser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.XML;

import com.ddbj.ld.bean.JgaStudyBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class JgaStudyParser {
    private ParserHelper parserHelper;

    // TODO name
    public List<JgaStudyBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject studySet  = XML.toJSONObject(xml);
            JSONArray studyArray = studySet.getJSONObject("STUDY_SET").getJSONArray("STUDY");

            List<JgaStudyBean> jgaStudyBeanList = new ArrayList<>();

            for(int n = 0; n < studyArray.length(); n++) {
                JSONObject study      = studyArray.getJSONObject(n);
                JSONObject descriptor = study.getJSONObject("DESCRIPTOR");

                String properties  = study.toString();
                String identifier  = study.getString("accession");
                String title       = descriptor.getString("STUDY_TITLE");
                String description = descriptor.getString("STUDY_ABSTRACT");

                JgaStudyBean jgaStudyBean = new JgaStudyBean();
                jgaStudyBean.setProperties(properties);
                jgaStudyBean.setIdentifier(identifier);
                jgaStudyBean.setTitle(title);
                jgaStudyBean.setDescription(description);

                jgaStudyBeanList.add(jgaStudyBean);
            }

            return jgaStudyBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }
}
