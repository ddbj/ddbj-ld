package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.bioproject.BioProject;
import com.ddbj.ld.data.beans.bioproject.CenterID;
import com.ddbj.ld.data.beans.bioproject.Converter;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.DatesBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class BioProjectService {

    private ParserHelper parserHelper;
    private UrlHelper urlHelper;
    private SRAAccessionsDao sraAccessionsDao;
    private HashMap<String, List<String>> errorInfo;

    public List<JsonBean> getBioProject(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();
            // ファイルごとにエラー情報を分けたいため、初期化
            this.errorInfo = new HashMap<>();

            // 関係性を取得するテーブル
            var bioProjectSubmissionTable = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.SUBMISSION.toString();
            var bioProjectStudyTable      = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.STUDY.toString();

            var bioProjectType = TypeEnum.BIOPROJECT;
            var submissionType = TypeEnum.SUBMISSION;
            var studyType      = TypeEnum.STUDY;

            var isStarted = false;
            var startTag  = XmlTagEnum.BIO_PROJECT_START.getItem();
            var endTag    = XmlTagEnum.BIO_PROJECT_END.getItem();

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
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    BioProject properties = this.getProperties(json, xmlPath);

                    if(null == properties) {
                        log.debug("Skip this metadata.");

                        continue;
                    }

                    var projectPackage = properties.getBioProjectPackage();

                    var project = projectPackage
                            .getProject()
                            .getProject();

                    var identifier = project
                            .getProjectID()
                            .getArchiveID()
                            .getAccession();

                    var projectDescr = project.getProjectDescr();

                    var title = projectDescr.getTitle();

                    var description = projectDescr.getDescription();

                    var name = projectDescr.getName();

                    var type = TypeEnum.BIOPROJECT.getType();

                    var url = this.urlHelper.getUrl(type, identifier);

                    // FIXME Mapping
                    List<SameAsBean> sameAs = null;
                    var projectId = project.getProjectID();
                    var centerIds = projectId.getCenterID();
                    for (CenterID centerId : centerIds) {
                        if ("GEO" != centerId.getCenter()) {
                            continue;
                        }
                        SameAsBean item = new SameAsBean();
                        String sameAsId = centerId.getContent();
                        String sameAsType = "";
                        String sameAsUrl = "";
                        item.setIdentifier(sameAsId);
                        item.setType(sameAsType);
                        item.setUrl(sameAsUrl);
                        sameAs.add(item);
                    };

                    var isPartOf = IsPartOfEnum.BIOPROJECT.getIsPartOf();

                    var projectTypeSubmission = project
                            .getProjectType()
                            .getProjectTypeSubmission();

                    // 生物名とIDを設定
                    var organismTarget =
                            null == projectTypeSubmission
                                    ? null
                                    : projectTypeSubmission
                                    .getTarget()
                                    .getOrganism();

                    var organismName =
                            null == organismTarget
                                    ? null
                                    : organismTarget.getOrganismName();

                    var organismIdentifier =
                            null == organismTarget
                                    ? null
                                    : organismTarget.getTaxID();

                    var organism = this.parserHelper.getOrganism(organismName, organismIdentifier);

                    // FIXME BioSampleとの関係も明らかにする
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var studyDbXrefs          = this.sraAccessionsDao.selRelation(identifier, bioProjectStudyTable, bioProjectType, studyType);
                    var submissionDbXrefs     = this.sraAccessionsDao.selRelation(identifier, bioProjectSubmissionTable, bioProjectType, submissionType);

                    dbXrefs.addAll(studyDbXrefs);
                    dbXrefs.addAll(submissionDbXrefs);

                    var distribution = this.parserHelper.getDistribution(TypeEnum.BIOPROJECT.getType(), identifier);

                    // SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.BIOPROJECT.toString());
                    String dateCreated = datas.getDateCreated();
                    String dateModified = datas.getDateModified();
                    String datePublished = datas.getDatePublished();

                    var bean = new JsonBean(
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

                    jsonList.add(bean);
                }
            }

            for(Map.Entry<String, List<String>> entry : this.errorInfo.entrySet()) {
                // パース失敗したJsonの統計情報を出す
                var message = entry.getKey();
                var values  = entry.getValue();
                // パース失敗したサンプルのJsonを1つピックアップ
                var json    = values.get(0);
                var count   = values.size();

                log.error("Converting json to bean is failed:" + "\t" + message + "\t" + count + "\t" + xmlPath + "\t" + json);
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    private BioProject getProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            return Converter.fromJsonString(json);
        } catch (IOException e) {
            var message = e.getLocalizedMessage()
                    .replaceAll("\n at.*.", "")
                    .replaceAll("\\(.*.", "");

            log.debug("Converting json to bean is failed:" + "\t" + xmlPath + "\t" + message + "\t" + json);

            List<String> values;

            if(null == (values = this.errorInfo.get(message))) {
                values = new ArrayList<>();
            }

            values.add(json);

            this.errorInfo.put(message, values);

            return null;
        }
    }
}
