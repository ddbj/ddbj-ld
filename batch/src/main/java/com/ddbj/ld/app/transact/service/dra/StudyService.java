package com.ddbj.ld.app.transact.service.dra;

import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.dra.study.STUDYClass;
import com.ddbj.ld.data.beans.dra.study.StudyConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class StudyService {
    private final CommonService commonService;
    private final ParserHelper parserHelper;
    private final UrlHelper urlHelper;
    private final SRAAccessionsDao sraAccessionsDao;

    private final String bioProjectStudyTable = TypeEnum.BIOPROJECT + "_" + TypeEnum.STUDY;
    private final String studySubmissionTable = TypeEnum.STUDY      + "_" + TypeEnum.SUBMISSION;

    public List<JsonBean> getStudy(final String xmlPath) {
        try (var br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            var sb = new StringBuilder();
            var jsonList = new ArrayList<JsonBean>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_STUDY_START.getItem();
            var endTag    = XmlTagEnum.DRA_STUDY_END.getItem();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入る可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    var properties = this.getProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // accesion取得
                    var identifier = properties.getAccession();

                    // Title取得
                    var descriptor = properties.getDescriptor();
                    var title = descriptor.getStudyTitle();

                    // Description 取得
                    var description = descriptor.getStudyDescription();

                    // name 取得
                    var name = properties.getAlias();
                    var type = TypeEnum.STUDY.getType();

                    // dra-study/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // 自分と同値の情報を保持するBioProjectを指定
                    var externalid = properties.getIdentifiers().getExternalID();
                    List<SameAsBean> sameAs = null;
                    if (externalid != null) {
                        sameAs = this.commonService.getSameAsBeans(externalid, TypeEnum.BIOPROJECT.getType());
                    }

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDはSampleのみの情報であるため空情報を設定
                    var organism = new OrganismBean();

                    //
                    var dbXrefs = new ArrayList<DBXrefsBean>();
                    var bioProjectStudyXrefs = this.sraAccessionsDao.selRelation(identifier, bioProjectStudyTable, TypeEnum.STUDY, TypeEnum.BIOPROJECT);
                    var studySubmissionXrefs = this.sraAccessionsDao.selRelation(identifier, studySubmissionTable, TypeEnum.STUDY, TypeEnum.SUBMISSION);

                    dbXrefs.addAll(bioProjectStudyXrefs);
                    dbXrefs.addAll(studySubmissionXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    /// SRA_Accessions.tabから日付のデータを取得
                    var datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.STUDY.toString());
                    var dateCreated = datas.getDateCreated();
                    var dateModified = datas.getDateModified();
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
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    private STUDYClass getProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            var bean = StudyConverter.fromJsonString(json);

            return bean.getStudy();
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }
}