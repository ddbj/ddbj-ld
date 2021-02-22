package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.core.parser.common.JsonParser;
import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.helper.DateHelper;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.bioproject.Package;
import com.ddbj.ld.data.beans.bioproject.*;
import com.ddbj.ld.data.beans.common.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BioProjectService {

    private ParserHelper parserHelper;
    private UrlHelper urlHelper;
    private DateHelper dateHelper;
    private SRAAccessionsDao sraAccessionsDao;
    private JsonParser jsonParser;

    public List<JsonBean> getBioProject(String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            // 関係性を取得するテーブル
            String bioProjectSubmissionTable = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.SUBMISSION.toString();
            String bioProjectStudyTable      = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.STUDY.toString();

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
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入ってくる可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var xml = sb.toString()
                            .replaceAll("<Processing","<Processing/><Processing")
                            .replaceAll("<CenterID","<CenterID/><CenterID")
                            .replaceAll("<LocalID","<LocalID/><LocalID")
                            .replaceAll("<ArchiveID","<ArchiveID/><ArchiveID")
                            .replaceAll("<ExternalLink","<ExternalLink/><ExternalLink")
                            .replaceAll("<dbXREF","<dbXREF/><dbXREF")
                            .replaceAll("<ID","<ID/><ID")
                            .replaceAll("<Grant","<Grant/><Grant")
                            .replaceAll("<Publication","<Publication/><Publication")
                            .replaceAll("<Keyword","<Keyword/><Keyword")
                            .replaceAll("<Relevance","<Relevance/><Relevance")
                            .replaceAll("<LocusTagPrefix","<LocusTagPrefix/><LocusTagPrefix")
                            .replaceAll("<UserTerm","<UserTerm/><UserTerm")
                            .replaceAll("<Replicon","<Replicon/><Replicon")
                            .replaceAll("<Count","<Count/><Count")
                            .replaceAll("<Synonym","<Synonym/><Synonym")
                            .replaceAll("<Data","<Data/><Data")
                            .replaceAll("<Hierarchical","<Hierarchical/><Hierarchical")
                            .replaceAll("<PeerProject","<PeerProject/><PeerProject")
                            .replaceAll("<Link","<Link/><Link")
                            .replaceAll("<Organization","<Organization/><Organization")
                            .replaceAll("<Shape","<Shape/><Shape")
                            .replaceAll("<IntendedDataTypeSet","<IntendedDataTypeSet/><IntendedDataTypeSet")
                            .replaceAll("<SecondaryArchiveID","<SecondaryArchiveID/><SecondaryArchiveID")
                            .replaceAll("<BioSampleSet","<BioSampleSet/><BioSampleSet")
                            .replaceAll("<DataType","<DataType/><DataType")
                            // FIXME ほかのAuthorHogeHogeと区別するための暫定措置
                            .replaceAll("<Author ","<Author/><Author ")
                            // FIXME ほかのOrganismHogeHogeと区別するための暫定措置
                            .replaceAll("<Organism ","<Organism/><Organism ");

                    JSONObject obj = XML.toJSONObject(xml);

                    // 一部のプロパティを配列にするために増やしたタグ由来のブランクの項目を削除
                    String properties = obj.toString()
                            .replaceAll("/\"\",{2,}/ ", "")
                            .replaceAll("\\[\"\",", "\\[")
                            .replaceAll(",\"\",", ",")
                            .replaceAll("\\[\"\"]", "\\[]")
                            .replaceAll("\\[\"\",\"\"]", "\\[]")
                            .replaceAll("\"\",\\{", "{");

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    BioProject bioProject = this.getProperties(properties, xmlPath);

                    if(null == bioProject) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    Package projectPackage = bioProject.getBioProjectPackage();

                    ProjectProject project = projectPackage
                            .getProject()
                            .getProject();

                    String identifier = project
                            .getProjectID()
                            .getArchiveID()
                            .get(0)
                            .getAccession();

                    ProjectDescr projectDescr = project.getProjectDescr();

                    String title = projectDescr.getTitle();

                    String description = projectDescr.getDescription();

                    String name = projectDescr.getName();

                    String type = TypeEnum.BIOPROJECT.getType();

                    String url = this.urlHelper.getUrl(type, identifier);

                    // FIXME Mapping
                    List<SameAsBean> sameAs = null;

                    String isPartOf = IsPartOfEnum.BIOPROJECT.getIsPartOf();

                    ProjectTypeSubmission projectTypeSubmission = project
                            .getProjectType()
                            .getProjectTypeSubmission();

                    // FIXME Organismとする項目はこれであっているのか確認が必要
                    List<TargetOrganism> organismList =
                              null == projectTypeSubmission
                                ? null
                                : projectTypeSubmission
                                  .getTarget()
                                  .getOrganism();

                    String organismName =
                            null == organismList
                                    ? null
                                    : organismList.get(0).getOrganismName();

                    String organismIdentifier =
                            null == organismList
                                    ? null
                                    : organismList.get(0).getTaxID();

                    OrganismBean organism = parserHelper.getOrganism(organismName, organismIdentifier);

                    // FIXME BioSampleとの関係も明らかにする
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    List<DBXrefsBean> studyDbXrefs      = sraAccessionsDao.selRelation(identifier, bioProjectStudyTable, bioProjectType, studyType);
                    List<DBXrefsBean> submissionDbXrefs = sraAccessionsDao.selRelation(identifier, bioProjectSubmissionTable, bioProjectType, submissionType);

                    dbXrefs.addAll(studyDbXrefs);
                    dbXrefs.addAll(submissionDbXrefs);

                    List<DistributionBean> distribution = this.parserHelper.getDistribution(TypeEnum.BIOPROJECT.getType(), identifier);

                    Submission submission = projectPackage
                            .getProject()
                            .getSubmission();

                    // FIXME 日付のデータの取得元を明らかにし、日付のデータを取得できるようにする
                    String dateCreated = null;

                    String dateModified = null;

                    String datePublished = null;

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
                            // FIXME 暫定対応
                            jsonParser.parse(bioProject),
                            distribution,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    jsonList.add(bean);
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    private BioProject getProperties(String json, String xmlPath) {
        try {
            return Converter.fromJsonString(json);
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }
}
