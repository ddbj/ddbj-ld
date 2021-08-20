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
import com.ddbj.ld.data.beans.common.*;
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

    private final ParserHelper parserHelper;
    private final UrlHelper urlHelper;
    private final SRAAccessionsDao sraAccessionsDao;
    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<JsonBean> getBioProject(final String xmlPath) {
        try (var br = new BufferedReader(new FileReader(xmlPath));) {

            var line = "";
            var sb = new StringBuilder();
            var jsonList = new ArrayList<JsonBean>();
            // ファイルごとにエラー情報を分けたいため、初期化
            this.errorInfo = new HashMap<>();

            // 関係性を取得するテーブル
            var bioProjectSubmissionTable = TypeEnum.BIOPROJECT + "_" + TypeEnum.SUBMISSION;
            var bioProjectStudyTable      = TypeEnum.BIOPROJECT + "_" + TypeEnum.STUDY;

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
                    var properties = this.getProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    var projectPackage = properties.getBioProjectPackage();

                    var project = projectPackage
                            .get(0)
                            .getProject()
                            .getProject();

                    var identifier = project
                            .getProjectID()
                            .getArchiveID()
                            .get(0)
                            .getAccession();

                    var projectDescr = project.getProjectDescr();

                    var title = projectDescr.getTitle();

                    var description = projectDescr.getDescription();

                    var name = projectDescr.getName();

                    var type = TypeEnum.BIOPROJECT.getType();

                    var url = this.urlHelper.getUrl(type, identifier);

                    List<SameAsBean> sameAs = null;
                    var projectId = project.getProjectID();
                    var centerIds = projectId.getCenterID();

                    if(null != centerIds) {
                        // DDBJ出力分だとCenterIDが存在しないため、Null値チェックをする
                        for (CenterID centerId : centerIds) {

                            if ("GEO".equals(centerId.getCenter())) {

                                SameAsBean item = new SameAsBean();
                                String sameAsId = centerId.getContent();
                                String sameAsType = "";
                                String sameAsUrl = "";
                                item.setIdentifier(sameAsId);
                                item.setType(sameAsType);
                                item.setUrl(sameAsUrl);
                                sameAs.add(item);

                                break;
                            }
                        }
                    }

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

                    var dbXrefs           = new ArrayList<DBXrefsBean>();
                    var studyDbXrefs      = this.sraAccessionsDao.selRelation(identifier, bioProjectStudyTable, bioProjectType, studyType);
                    var submissionDbXrefs = this.sraAccessionsDao.selRelation(identifier, bioProjectSubmissionTable, bioProjectType, submissionType);

                    dbXrefs.addAll(studyDbXrefs);
                    dbXrefs.addAll(submissionDbXrefs);

                    var distribution = this.parserHelper.getDistribution(TypeEnum.BIOPROJECT.getType(), identifier);

                    // SRA_Accessions.tabから日付のデータを取得
                    // FIXME ここからは取れない、XMLから取得すること
                    var datas         = this.sraAccessionsDao.selDates(identifier, TypeEnum.BIOPROJECT.toString());
                    var dateCreated   = datas.getDateCreated();
                    var dateModified  = datas.getDateModified();
                    var datePublished = datas.getDatePublished();

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

                    // TODO 10万件程度ごとにESに登録できるようにする
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
