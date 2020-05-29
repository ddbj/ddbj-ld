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

    // TODO name, dateCreated, dateModified, datePublished
    public List<JgaStudyBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject jgaStudySet  = XML.toJSONObject(xml).getJSONObject("STUDY_SET");
            Object jgaStudyObject   = jgaStudySet.get("STUDY");

            List<JgaStudyBean> jgaStudyBeanList = new ArrayList<>();

            if(jgaStudyObject instanceof JSONArray) {
                JSONArray jgaStudyArray = ((JSONArray)jgaStudyObject);

                for(int i = 0; i < jgaStudyArray.length(); i++) {
                    JSONObject jgaStudy  = jgaStudyArray.getJSONObject(i);
                    JgaStudyBean jgaStudyBean = getBean(jgaStudy);
                    jgaStudyBeanList.add(jgaStudyBean);
                }
            } else {
                JSONObject jgaStudy = (JSONObject)jgaStudyObject;
                JgaStudyBean jgaStudyBean = getBean(jgaStudy);
                jgaStudyBeanList.add(jgaStudyBean);
            }

            return jgaStudyBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private JgaStudyBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");

        JSONObject descriptor;
        String title = null;
        String description = null;

        if(obj.has("DESCRIPTOR")) {
            descriptor = obj.getJSONObject("DESCRIPTOR");

            title =
                      descriptor.has("STUDY_TITLE")
                    ? descriptor.getString("STUDY_TITLE")
                    : null;

            description =
                      descriptor.has("STUDY_ABSTRACT")
                    ? descriptor.getString("STUDY_ABSTRACT")
                    : null;
        }

        String properties  = obj.toString();

        JgaStudyBean jgaStudyBean = new JgaStudyBean();
        jgaStudyBean.setIdentifier(identifier);
        jgaStudyBean.setTitle(title);
        jgaStudyBean.setDescription(description);
        jgaStudyBean.setProperties(properties);

        return jgaStudyBean;
    }
}
