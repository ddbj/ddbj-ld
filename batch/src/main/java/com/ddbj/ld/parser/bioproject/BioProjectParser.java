package com.ddbj.ld.parser.bioproject;

import com.ddbj.ld.bean.bioproject.*;
import com.ddbj.ld.bean.common.*;
import com.ddbj.ld.common.constant.IsPartOfEnum;
import com.ddbj.ld.common.constant.TypeEnum;
import com.ddbj.ld.common.helper.DateHelper;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.dao.dra.SRAAccessionsDao;
import com.ddbj.ld.parser.common.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.XML;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class BioProjectParser {

    private ParserHelper parserHelper;
    private UrlHelper urlHelper;
    private DateHelper dateHelper;
    private JsonParser jsonParser;
    private SRAAccessionsDao sraAccessionsDao;

    public Map<String, String> parse(String xmlFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlFile));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> beanList = new ArrayList<>();
            Map<String, String> resultMap = new HashMap<>();

            // 関係性を取得するテーブル
            String bioProjectSubmissionTable = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.SUBMISSION.toString();
            String bioProjectStudyTable      = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.STUDY.toString();

            // 使用するObjectMapper
            ObjectMapper mapper = jsonParser.getMapper();
            TypeEnum bioProjectType = TypeEnum.BIOPROJECT;
            TypeEnum submissionType = TypeEnum.SUBMISSION;
            TypeEnum studyType      = TypeEnum.STUDY;

            boolean isStarted = false;
            String startTag   = "<Package>";
            String endTag     = "</Package>";

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                }

                if(isStarted) {
                    sb.append(line);
                }

                if(line.contains(endTag)) {
                    String properties = XML.toJSONObject(sb.toString()).toString();

                    // FIXME ここは結構キツいかも
                    BioProject bioProject = this.getProperties(properties, xmlFile);

                    if(null == bioProject) {
                        log.info("Skip this metadata.");
                        continue;
                    }

                    ProjectProject Project = bioProject
                            .getBioProjectPackage()
                            .getProject()
                            .getProject();

                    ProjectDescr projectDescr = Project.getProjectDescr();

                    String identifier = Project
                            .getProjectID()
                            .getArchiveID()
                            .getAccession();

                    String title = projectDescr.getTitle();

                    String description = projectDescr.getDescription();

                    String name = projectDescr.getName();

                    String type = TypeEnum.BIOPROJECT.getType();

                    String url = this.urlHelper.getUrl(type, identifier);

                    // FIXME Mapping
                    List<SameAsBean> sameAs = null;

                    String isPartOf = IsPartOfEnum.BIOPROJECT.getIsPartOf();

                    Organism organismBean = Project
                            .getProjectType()
                            .getProjectTypeTopSingleOrganism()
                            .getOrganism();
                    String organismName       = null == organismBean ? null : organismBean.getOrganismName();
                    String organismIdentifier = null == organismBean ? null : organismBean.getTaxID();
                    OrganismBean organism     = null == organismBean ? null : this.parserHelper.getOrganism(organismName, organismIdentifier);

                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    List<DBXrefsBean> studyDbXrefs      = sraAccessionsDao.selRelation(identifier, bioProjectStudyTable, bioProjectType, studyType);
                    List<DBXrefsBean> submissionDbXrefs = sraAccessionsDao.selRelation(identifier, bioProjectSubmissionTable, bioProjectType, submissionType);

                    dbXrefs.addAll(studyDbXrefs);
                    dbXrefs.addAll(submissionDbXrefs);

                    List<DistributionBean> distribution = this.parserHelper.getDistribution(TypeEnum.BIOPROJECT.getType(), identifier);

                    Publication publication = Project
                            .getProjectDescr()
                            .getPublication();

                    String dateCreated = null;
                    String dateModified = null;

                    String datePublished = null == publication ? null : this.dateHelper.parse(publication.getDate());

                    JsonBean bean = new JsonBean(
                            identifier,
                            title,
                            description,
                            name,
                            type,
                            url,
                            sameAs,
                            isPartOf,
                            organism,
                            dbXrefs,
                            properties,
                            distribution,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    beanList.add(bean);

                    resultMap.put(identifier, jsonParser.parse(bean, mapper));

                    sb = new StringBuilder();
                }
            }

            return resultMap;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlFile);

            return null;
        }
    }

    private BioProject getProperties(String json, String xmlFile) {
        try {
            return Converter.fromJsonString(json);
        } catch (JsonProcessingException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlFile);
            log.error(e.getMessage());

            return null;
        }
    }
}
