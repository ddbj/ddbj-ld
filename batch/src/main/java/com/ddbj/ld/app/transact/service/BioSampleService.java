package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.common.helper.BulkHelper;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.biosample.BioSample;
import com.ddbj.ld.data.beans.biosample.Converter;
import com.ddbj.ld.data.beans.biosample.SampleId;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.DatesBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BioSampleService {
    private final ConfigSet config;
    private final ParserHelper parserHelper;
    private final SearchModule searchModule;
    private final SRAAccessionsDao sraAccessionsDao;
    private final UrlHelper urlHelper;

    private HashMap<String, List<String>> errorInfo;
    private StringBuilder sb;

    public List<JsonBean> getBioSample(final String xmlPath) {
        log.info("1.UsedMemory:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+ "/" + Runtime.getRuntime().maxMemory());
        XmlReader reader = new XmlReader(new File(xmlPath));
//        try (Stream<String> stream = Files.lines(Paths.get(xmlPath))) {
        try {
            log.info("2.UsedMemory:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+ "/" + Runtime.getRuntime().maxMemory());
            List<JsonBean> jsonList = new ArrayList<>();
            // ファイルごとにエラー情報を分けたいため、初期化
            this.errorInfo = new HashMap<>();

            // 関係性を取得するテーブル
            var bioSampleSampleTable      = TypeEnum.BIOSAMPLE + "_" + TypeEnum.SAMPLE;
            var bioSampleExperimentTable  = TypeEnum.BIOSAMPLE + "_" + TypeEnum.EXPERIMENT;

            var startTag  = XmlTagEnum.BIO_SAMPLE_START.getItem();
            var endTag    = XmlTagEnum.BIO_SAMPLE_END.getItem(
            while (reader.hasNext()) {
//            stream.forEach(line -> {
                String line = reader.readLine();
                log.info("3.UsedMemory:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+ "/" + Runtime.getRuntime().maxMemory());
                // XMLファイルの1,2行目は判定対象外
                if (line.matches("^(<\\?xml ).*") || line.matches("<BioSampleSet>")) {
//                    return;
                    continue;
                }

                // 開始要素を判断する
                if (line.contains(startTag)) {
                    sb = new StringBuilder();
                }

                sb.append(line);

                // 2つ以上入ってくる可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if (line.contains(endTag)) {
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    BioSample properties = this.getProperties(json, xmlPath);

                    if (null == properties) {
                        log.error("Skip this metadata.");
//                        return;
                        continue;
                    }

                    var biosample = properties.getBioSample();

                    // accesion取得
                    var ids = biosample.getIDS();
                    var idlst = ids.getID();
                    String identifier = null;
//                    for (SampleId id : idlst) {
//                        // FIXME contentでいいか
//                        if (!"BioSample".equals(id.getDB())) {
//                            continue;
//                        }
//                        identifier = id.getContent();
//                    };

                    // Title取得
                    var descriptions = biosample.getDescription();
                    var title = descriptions.getTitle();

                    // Description 取得
                    // FIXME 取得するパラグラフの値の選定方法(list.get(0)でいいか)
                    var comment = descriptions.getComment();
                    String description = null;
                    if (null != comment) {
//                        log.error("id : " + identifier);
//                        log.error("json : " + json);

                        if (null != comment.getParagraph()) {
                            description = comment.getParagraph().get(0);
                        }
                    }

                    // name 取得
                    var name = descriptions.getSampleName();

                    // typeの設定
                    var type = TypeEnum.BIOSAMPLE.getType();

                    // bioproject/SAMN???????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // 自分と同値の情報を保持するデータを指定
                    List<SameAsBean> sameAs = new ArrayList<>();
                    for (SampleId id : idlst) {
                        if (!"SRA".equals(id.getNamespace())) {
                            continue;
                        }
                        SameAsBean item = new SameAsBean();
                        String sameAsId = id.getContent();
                        String sameAsType = TypeEnum.SAMPLE.getType();
                        String sameAsUrl = this.urlHelper.getUrl(type, sameAsId);
                        item.setIdentifier(sameAsId);
                        item.setType(sameAsType);
                        item.setUrl(sameAsUrl);
                        sameAs.add(item);
                    };

                    // "BIOSAMPLE"固定
                    var isPartOf = IsPartOfEnum.BIOPSAMPLE.getIsPartOf();

                    // 生物名とIDはSampleのみの情報であるため空情報を設定
                    var organisms = descriptions.getOrganism().get(0);

                    // 生物名とIDを設定
                    var organismName = null == organisms.getOrganismName() ? null :  organisms.getOrganismName();
                    var organismIdentifier = organisms.getTaxonomyID();

                    var organism = this.parserHelper.getOrganism(organismName, organismIdentifier);

                    // FIXME BioSampleとの関係も明らかにする
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var studyDbXrefs          = sraAccessionsDao.selRelation(identifier, bioSampleSampleTable, TypeEnum.BIOSAMPLE, TypeEnum.SAMPLE);
                    var submissionDbXrefs     = sraAccessionsDao.selRelation(identifier, bioSampleExperimentTable, TypeEnum.BIOSAMPLE, TypeEnum.EXPERIMENT);

                    dbXrefs.addAll(studyDbXrefs);
                    dbXrefs.addAll(submissionDbXrefs);

                    var distribution = this.parserHelper.getDistribution(TypeEnum.BIOPROJECT.getType(), identifier);

                    // SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = sraAccessionsDao.selDates(identifier, TypeEnum.BIOSAMPLE.toString());
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
                    log.info("jsonList.size:" + jsonList.size());
                    log.info("4.UsedMemory:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+ "/" + Runtime.getRuntime().maxMemory());
                }

                // 巨大ファイル対応
                var maximumRecord = config.other.maximumRecord;
                if (jsonList.size() >= maximumRecord) {
                    BulkHelper.extract(jsonList, maximumRecord, _jsonList -> {
                        searchModule.bulkInsert(TypeEnum.BIOSAMPLE.getType(), _jsonList);
                    });
                    jsonList.clear();
                }
//            });
            }

            reader.close();
            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);
            try {
                reader.close();
            } catch (IOException ioException) {
            }
            return null;
        }
    }

    public void printErrorInfo(final String xmlPath) {
        for (Map.Entry<String, List<String>> entry : this.errorInfo.entrySet()) {
            // パース失敗したJsonの統計情報を出す
            var message = entry.getKey();
            var values  = entry.getValue();
            // パース失敗したサンプルのJsonを1つピックアップ
            var json    = values.get(0);
            var count   = values.size();

            log.error("Converting json to bean is failed:" + "\t" + message + "\t" + count + "\t" + xmlPath + "\t" + json);
        }
    }

    private BioSample getProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            return Converter.fromJsonString(json);
        } catch (IOException e) {
            var message = e.getLocalizedMessage()
                    .replaceAll("\n at.*.", "")
                    .replaceAll("\\(.*.", "");

            log.error("Converting json to bean is failed:" + "\t" + xmlPath + "\t" + message + "\t" + json);

            List<String> values;

            if (null == (values = this.errorInfo.get(message))) {
                values = new ArrayList<>();
            }

            values.add(json);

            this.errorInfo.put(message, values);

            return null;
        }
    }
}