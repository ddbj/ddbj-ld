package com.ddbj.ld.app.transact.service.jga;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.transact.dao.jga.DateDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.OrganismEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.jga.study.STUDYClass;
import com.ddbj.ld.data.beans.jga.study.StudyConverter;
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
public class JgaStudyService {

    private final ConfigSet config;

    private final DateDao dateDao;

    public List<JsonBean> getStudy(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath))) {
            String line;
            StringBuilder sb = null;
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_STUDY_START.getItem();
            var endTag    = XmlTagEnum.JGA_STUDY_END.getItem();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                if(line.contains(endTag)) {
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json文字列をバリデーションにかけてから、Beanに変換する
                    var properties = this.getProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    var identifier = properties.getAccession();

                    var descriptor = properties.getDescriptor();

                    var title = descriptor.getStudyTitle();
                    var description = descriptor.getStudyAbstract();
                    // FIXME nameのマッピング
                    String name = null;

                    var type = TypeEnum.JGA_STUDY.getType();

                    // FIXME Beanの取得処理
                    var url = "";
//                    var url = this.urlHelper.getUrl(type, identifier);

                    // FIXME SameAsのマッピング(SECONDARY_IDか？
                    List<SameAsBean> sameAs = null;

                    var isPartOf = IsPartOfEnum.JGA.getIsPartOf();

                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();

                    // FIXME Beanの取得処理
//                    var organism     = this.jsonModule.getOrganism(organismName, organismIdentifier);
//                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    List<DBXrefsBean> dbXrefs = new ArrayList<>();

                    // オブジェクト間の関係性を取得する Study (1) → Dataset (n) → Policy (1) → DAC (NBDC, 1)
                    // ExperimentとDataを経由してDatasetのレコードを取得
                    // Analysisを経由してDatasetのレコードを取得
                    // DatasetからPolicyを取得
                    // PolicyからDacを取得
                    // TODO 各オブジェクトのdbXrefs

                    var dateInfo = this.dateDao.selJgaDate(identifier);

                    if(null == dateInfo) {
                        log.warn("Date information is nothing. Skip this record. accession:" + identifier);
                        return null;
                    }

                    // TODO　esSimpleDateFormatの日付文字列とする
                    var datePublished = "";
                    var dateCreated   = "";
                    var dateModified  = "";

                    var bean = new JsonBean(
                            identifier,
                            title,
                            description,
                            name,
                            type,
                            url,
                            null,
                            isPartOf,
                            null,
                            dbXrefs,
                            properties,
                            null,
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
