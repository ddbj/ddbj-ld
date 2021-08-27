package com.ddbj.ld.app.transact.service.dra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.DatesBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.dra.sample.SAMPLEClass;
import com.ddbj.ld.data.beans.dra.sample.SampleConverter;
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
public class DraSampleService {
    private final JsonModule jsonModule;
    private final SRAAccessionsDao sraAccessionsDao;

    private final String bioSampleSampleTable = TypeEnum.BIOSAMPLE  + "_" + TypeEnum.SAMPLE;

    public List<JsonBean> getSample(final String xmlPath) {
        try (var br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_SAMPLE.start;
            var endTag    = XmlTagEnum.DRA_SAMPLE.end;

            // FIXME SRAAccessions.tabから取得できるようにする
            var status = StatusEnum.LIVE.status;
            var visibility = VisibilityEnum.PUBLIC.visibility;

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

                    // Description 取得
                    var description = properties.getDescription();

                    // name 取得
                    String name = properties.getAlias();

                    // typeの設定
                    var type = TypeEnum.SAMPLE.getType();

                    // dra-sample/[DES]RA??????
                    var url = this.jsonModule.getUrl(type, identifier);

                    // 自分と同値の情報を保持するデータを指定
                    var externalid = properties.getIdentifiers().getExternalID();
                    List<SameAsBean> sameAs = null;
                    if (externalid != null) {
                        sameAs = this.jsonModule.getSameAsBeans(externalid, TypeEnum.BIOSAMPLE.getType());
                    }

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDを設定
                    var samplename = properties.getSampleName();
                    var organismName       = samplename.getScientificName();
                    var organismIdentifier = samplename.getTaxonID();
                    var organism     = this.jsonModule.getOrganism(organismName, organismIdentifier);

                    // dbxrefの設定
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var bioSampleSampleXrefs = this.sraAccessionsDao.selRelation(identifier, bioSampleSampleTable, TypeEnum.SAMPLE, TypeEnum.BIOSAMPLE);

                    dbXrefs.addAll(bioSampleSampleXrefs);
                    var distribution = this.jsonModule.getDistribution(type, identifier);

                    // SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.SAMPLE.toString());
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
                            status,
                            visibility,
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

    private SAMPLEClass getProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            var bean = SampleConverter.fromJsonString(json);

            return bean.getSample();
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }
}