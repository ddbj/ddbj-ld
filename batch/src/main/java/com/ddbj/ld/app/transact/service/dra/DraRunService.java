package com.ddbj.ld.app.transact.service.dra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.dra.run.RUNClass;
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
public class DraRunService {
    private final JsonModule jsonModule;
    private final SRAAccessionsDao sraAccessionsDao;

    private final String experimentRunTable = TypeEnum.EXPERIMENT + "_" + TypeEnum.RUN;
    private final String runBioSampleTable  = TypeEnum.RUN        + "_" + TypeEnum.BIOSAMPLE;

    public List<JsonBean> getRun(final String xmlPath) {
        try (var br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            var sb = new StringBuilder();
            var jsonList = new ArrayList<JsonBean>();

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
                    var properties = this.getProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // accesion取得
                    var identifier = properties.getAccession();

                    // Title取得
                    var title = properties.getTitle();

                    // Description に該当するデータは存在しないためrunではnullを設定
                    String description = null;

                    // name 取得
                    var name = properties.getAlias();

                    // typeの設定
                    var type = TypeEnum.RUN.getType();

                    // dra-run/[DES]RA??????
                    var url = this.jsonModule.getUrl(type, identifier);

                    // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
                    var sameAs = new ArrayList<SameAsBean>();

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDはSampleのみの情報であるため空情報を設定
                    var organism = new OrganismBean();

                    var dbXrefs = new ArrayList<DBXrefsBean>();
                    var experimentRunXrefs = this.sraAccessionsDao.selRelation(identifier, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);
                    var runBioSampleXrefs  = this.sraAccessionsDao.selRelation(identifier, runBioSampleTable, TypeEnum.RUN, TypeEnum.BIOSAMPLE);
                    dbXrefs.addAll(experimentRunXrefs);
                    dbXrefs.addAll(runBioSampleXrefs);
                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    // SRA_Accessions.tabから日付のデータを取得
                    var datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.RUN.toString());
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

    private RUNClass getProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            var bean = RunConverter.fromJsonString(json);

            return bean.getRun();
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }
}