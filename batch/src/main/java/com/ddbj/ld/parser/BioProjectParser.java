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

                for(int i = 0; i < bioProjectArray.length(); i++) {
                    JSONObject bioProject  = bioProjectArray.getJSONObject(i);
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
        JSONObject project =
                 obj
                .getJSONObject("Project")
                .getJSONObject("Project");

        String identifier  =
                 project
                .getJSONObject("ProjectID")
                .getJSONObject("ArchiveID")
                .getString("accession");

        JSONObject projectDescr = project.getJSONObject("ProjectDescr");

        String name =
                  projectDescr.has("Name")
                ? projectDescr.getString("Name")
                : null;

        String title =
                  projectDescr.has("Title")
                ? projectDescr.getString("Title")
                : null;

        String description =
                  projectDescr.has("Description")
                ? projectDescr.getString("Description")
                : null;

        String properties = obj.toString();

        String dateCreated =
                  projectDescr.has("ProjectReleaseDate")
                ? projectDescr.getString("ProjectReleaseDate")
                : null;

        JSONObject publication =
                  projectDescr.has("Publication")
                ? projectDescr.getJSONObject("Publication")
                : null;

        String datePublished =
                  publication.has("date")
                ? publication.getString("date")
                : null;

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
