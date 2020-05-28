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

import com.ddbj.ld.bean.StudyBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class StudyParser {
    private ParserHelper parserHelper;

    // TODO name 日付系はSRAAccessionsから取得
    public List<StudyBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject studySet  = XML.toJSONObject(xml).getJSONObject("STUDY_SET");
            Object studyObject   = studySet.get("STUDY");

            List<StudyBean> studyBeanList = new ArrayList<>();

            if(studyObject instanceof JSONArray) {
                JSONArray studyArray = ((JSONArray)studyObject);

                for(int n = 0; n < studyArray.length(); n++) {
                    JSONObject study  = studyArray.getJSONObject(n);
                    StudyBean studyBean = getBean(study);
                    studyBeanList.add(studyBean);
                }
            } else {
                JSONObject study = (JSONObject)studyObject;
                StudyBean studyBean = getBean(study);
                studyBeanList.add(studyBean);
            }

            return studyBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private StudyBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");
        JSONObject descriptor = obj.getJSONObject("DESCRIPTOR");
        String title       = descriptor.getString("STUDY_TITLE");
        String description = descriptor.getString("STUDY_ABSTRACT");
        String properties  = obj.toString();

        StudyBean studyBean = new StudyBean();
        studyBean.setIdentifier(identifier);
        studyBean.setTitle(title);
        studyBean.setDescription(description);
        studyBean.setProperties(properties);

        return studyBean;
    }
}
