package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.common.constants.CenterEnum;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.data.beans.bioproject.CenterID;
import com.ddbj.ld.data.beans.bioproject.Converter;
import com.ddbj.ld.data.beans.bioproject.Package;
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

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public void register(
            final String path,
            final CenterEnum center,
            final boolean deletable
    ) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var jsonList = new ArrayList<JsonBean>();
            // ファイルごとにエラー情報を分けたいため、初期化
            this.errorInfo = new HashMap<>();

            var isStarted  = false;
            var startTag   = XmlTagEnum.BIO_PROJECT.start;
            var endTag     = XmlTagEnum.BIO_PROJECT.end;
            var ddbjPrefix = "PRJD";
            var maximumRecord = this.config.other.maximumRecord;
            var type = TypeEnum.BIOPROJECT.type;

            if(this.searchModule.existsIndex(type) && deletable) {
                this.searchModule.deleteIndex(type);
            }

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
                    var properties = this.getProperties(json, path);

                    if(null == properties) {
                        continue;
                    }

                    var project = properties
                            .getProject()
                            .getProject();

                    var identifier = project
                            .getProjectID()
                            .getArchiveID()
                            .get(0)
                            .getAccession();

                    // 他局出力のファイルならDDBJのアクセッションはスキップ
                    if(center != CenterEnum.DDBJ
                    && identifier.startsWith(ddbjPrefix)) {
                        continue;
                    }

                    var projectDescr = project.getProjectDescr();

                    var title = projectDescr.getTitle();

                    var description = projectDescr.getDescription();

                    var name = projectDescr.getName();

                    var url = this.jsonModule.getUrl(type, identifier);
                    List<SameAsBean> sameAs = null;
                    var projectId = project.getProjectID();
                    var centerIds = projectId.getCenterID();

                    if(null != centerIds) {
                        // DDBJ出力分だとCenterIDが存在しないため、Null値チェックをする
                        for (CenterID centerId : centerIds) {

                            if ("GEO".equals(centerId.getCenter())) {
                                sameAs = new ArrayList<>();
                                var item = new SameAsBean();
                                var sameAsId = centerId.getContent();
                                var sameAsType = "GEO";
                                var sameAsUrl = new StringBuilder("https://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=").append(sameAsId).toString();
                                item.setIdentifier(sameAsId);
                                item.setType(sameAsType);
                                item.setUrl(sameAsUrl);
                                sameAs.add(item);

                                break;
                            }
                        }
                    }

                    var isPartOf = IsPartOfEnum.BIOPROJECT.isPartOf;

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

                    var organism = this.jsonModule.getOrganism(organismName, organismIdentifier);

                    var dbXrefs = new ArrayList<DBXrefsBean>();

                    // dra-studyを取得
                    var externalLink = projectDescr.getExternalLink();

                    if(null != externalLink) {
                        for (var ex : externalLink) {
                            var dbXREF = ex.getDBXREF();

                            if(null != dbXREF
                            && "SRA".equals(dbXREF.getDB())
                            && null != dbXREF.getID()) {
                                var id = dbXREF.getID();

                                var bean = new DBXrefsBean(
                                        id,
                                        TypeEnum.STUDY.type,
                                        this.jsonModule.getUrl(TypeEnum.STUDY.type, id)
                                );

                                dbXrefs.add(bean);
                            }
                        }
                    }

                    // biosampleを取得
                    var locustTagList = projectDescr.getLocusTagPrefix();

                    if (null != locustTagList) {
                        for(var locus: locustTagList) {
                            var bioSampleId = locus.getBiosampleID();

                            if(null != bioSampleId) {
                                var bean = new DBXrefsBean(
                                        bioSampleId,
                                        TypeEnum.BIOSAMPLE.type,
                                        this.jsonModule.getUrl(TypeEnum.BIOSAMPLE.type, bioSampleId)
                                );

                                dbXrefs.add(bean);
                            }
                        }
                    }


                    var distribution = this.jsonModule.getDistribution(TypeEnum.BIOPROJECT.type, identifier);

                    // FIXME DDBJ出力分のXMLにはSubmissionタグがないため、別の取得方法が必要
                    var submission = properties.getSubmission();
                    var datePublished = null == projectDescr.getProjectReleaseDate() ? null : this.jsonModule.parseOffsetDateTime(projectDescr.getProjectReleaseDate());
                    // 作成日次、更新日時がない場合は公開日時の値を代入する
                    // NCBIのサイトもそのような表示となっている https://www.ncbi.nlm.nih.gov/bioproject/16
                    var dateCreated   = null == submission || null == submission.getSubmitted() ? datePublished : this.jsonModule.parseLocalDate(submission.getSubmitted());
                    var dateModified  = null == submission || null == submission.getLastUpdate() ? datePublished : this.jsonModule.parseLocalDate(submission.getLastUpdate());

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

                    if(jsonList.size() == maximumRecord) {
                        this.searchModule.bulkInsert(type, jsonList);
                        jsonList.clear();
                    }
                }
            }

            if(jsonList.size() > 0) {
                this.searchModule.bulkInsert(type, jsonList);
            }

            for(Map.Entry<String, List<String>> entry : this.errorInfo.entrySet()) {
                // パース失敗したJsonの統計情報を出す
                var message = entry.getKey();
                var values  = entry.getValue();
                // パース失敗したサンプルのJsonを1つピックアップ
                var json    = values.get(0);
                var count   = values.size();

                log.error("Converting json to bean is failed:\t{}\t{}\t{}\t{}", message, count, path, json);
            }

        } catch (IOException e) {
            log.error("Not exists file:{}", path, e);
        }
    }

    private Package getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = Converter.fromJsonString(json);

            return bean.getBioProjectPackage();
        } catch (IOException e) {
            log.error("Converting metadata to bean is failed. xml path: {}, json:{}", path, json, e);

            var message = e.getLocalizedMessage()
                    .replaceAll("\n at.*.", "")
                    .replaceAll("\\(.*.", "");

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
