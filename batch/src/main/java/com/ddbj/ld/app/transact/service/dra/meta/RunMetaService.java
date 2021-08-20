package com.ddbj.ld.app.transact.service.dra.meta;

import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.dra.run.Run;
import com.ddbj.ld.data.beans.dra.run.RunConverter;
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
public class RunMetaService {
    private final ParserHelper parserHelper;
    private final UrlHelper urlHelper;
    private SRAAccessionsDao sraAccessionsDao;

    private final String experimentRunTable        = TypeEnum.EXPERIMENT + "_" + TypeEnum.RUN;
    private final String runBioSampleTable         = TypeEnum.RUN        + "_" + TypeEnum.BIOSAMPLE;

    public List<JsonBean> getRun(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_RUN_START.getItem();
            var endTag    = XmlTagEnum.DRA_RUN_END.getItem();

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
                    Run properties = this.getProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // JsonBean設定項目の取得
                    var run = properties.getRun();

                    // accesion取得
                    var identifier = run.getAccession();

                    // Title取得
                    var title = run.getTitle();

                    // Description に該当するデータは存在しないためrunではnullを設定
                    String description = null;

                    // name 取得
                    String name = run.getAlias();

                    // typeの設定
                    var type = TypeEnum.RUN.getType();

                    // dra-run/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
                    List<SameAsBean> sameAs = new ArrayList<>();

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDはSampleのみの情報であるため空情報を設定
                    OrganismBean organism = new OrganismBean();

                    //
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var experimentRunXrefs = this.sraAccessionsDao.selRelation(identifier, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);
                    var runBioSampleXrefs  = this.sraAccessionsDao.selRelation(identifier, runBioSampleTable, TypeEnum.RUN, TypeEnum.BIOSAMPLE);
                    dbXrefs.addAll(experimentRunXrefs);
                    dbXrefs.addAll(runBioSampleXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    // SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.RUN.toString());
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

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    private Run getProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            return RunConverter.fromJsonString(json);
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }
}