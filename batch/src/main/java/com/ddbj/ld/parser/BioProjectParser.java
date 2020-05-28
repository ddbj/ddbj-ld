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
import com.ddbj.ld.bean.BioProjectBean;

@Component
@AllArgsConstructor
@Slf4j
public class BioProjectParser {
    private ParserHelper parserHelper;

    // TODO dateModified
    public List<BioProjectBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject packageSet  = XML.toJSONObject(xml).getJSONObject("PackageSet");
            Object bioProjectObject   = packageSet.get("Package");

            List<BioProjectBean> bioProjectBeanList = new ArrayList<>();

            if(bioProjectObject instanceof JSONArray) {
                JSONArray bioProjectArray = (JSONArray)bioProjectObject;

                for(int n = 0; n < bioProjectArray.length(); n++) {
                    JSONObject bioProject  = bioProjectArray.getJSONObject(n);
                    BioProjectBean bioProjectBean = getBean(bioProject);
                    bioProjectBeanList.add(bioProjectBean);
                }
            } else {
                JSONObject bioProject  = (JSONObject)bioProjectObject;
                BioProjectBean bioProjectBean = getBean(bioProject);
                bioProjectBeanList.add(bioProjectBean);
            }

            return bioProjectBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private BioProjectBean getBean(JSONObject obj) {
        JSONObject project = obj
                .getJSONObject("Project")
                .getJSONObject("Project");

        String identifier  = project
                .getJSONObject("ProjectID")
                .getJSONObject("ArchiveID")
                .getString("accession");

        JSONObject projectDescr = project.getJSONObject("ProjectDescr");

        String name          = projectDescr.getString("Name");
        String title         = projectDescr.getString("Title");
        String description   = projectDescr.getString("Description");
        String properties    = obj.toString();
        String dateCreated   = projectDescr.getString("ProjectReleaseDate");
        String datePublished = projectDescr
                .getJSONObject("Publication")
                .getString("date");

        BioProjectBean bioProjectBean = new BioProjectBean();
        bioProjectBean.setIdentifier(identifier);
        bioProjectBean.setName(name);
        bioProjectBean.setTitle(title);
        bioProjectBean.setDescription(description);
        bioProjectBean.setProperties(properties);
        bioProjectBean.setDateCreated(dateCreated);
        bioProjectBean.setDatePublished(datePublished);

        return bioProjectBean;
    }
}
