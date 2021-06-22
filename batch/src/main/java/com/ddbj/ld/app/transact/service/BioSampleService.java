package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.FileNameEnum;
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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void splitBioSample(final String xmlPath) {
        try(var reader = new BufferedReader(new FileReader(xmlPath))) {
            var startTag = XmlTagEnum.BIO_SAMPLE_START.getItem();
            var endTag = XmlTagEnum.BIO_SAMPLE_END.getItem();
            var setStartTag = "<BioSampleSet>\n";
            var setEndTag = "</BioSampleSet>";
            var isStarted = false;
            var recordSize = 0;
            var fileNo = 1;
            var xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            // 1万レコード単位でファイルを分けたい
            String line;
            var sb = new StringBuilder();
            sb.append(xmlHeader);
            sb.append(setStartTag);
            while ((line = reader.readLine()) != null) {
                if (line.contains(startTag)) {
                    isStarted = true;
                }
                if (isStarted && reader.ready()) {
                    sb.append("  " + line + "\n");
                }
                if (line.contains(endTag)) {
                    recordSize++;
                }
                if (recordSize == 100 || !reader.ready()) {
                    sb.append(setEndTag);
                    var path = xmlPath.substring(0, xmlPath.indexOf(FileNameEnum.BIOSAMPLE_XML.getFileName()));
                    var fileName = path + "split/" + "biosample_set." + fileNo + ".xml";
                    try (var bw = Files.newBufferedWriter(Paths.get(fileName));
                         var pw = new PrintWriter(bw, true)) {
                        pw.println(sb.toString());
                    } catch (IOException e) {
                    }
                    recordSize = 0;
                    sb = new StringBuilder();
                    sb.append(xmlHeader);
                    sb.append(setStartTag);
                    fileNo++;
                }
            }
        } catch (IOException e) {
        }
    }

    public List<JsonBean> getBioSample(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath))) {
            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();
            // ファイルごとにエラー情報を分けたいため、初期化
            this.errorInfo = new HashMap<>();

            // 関係性を取得するテーブル
            var bioSampleSampleTable      = TypeEnum.BIOSAMPLE + "_" + TypeEnum.SAMPLE;
            var bioSampleExperimentTable  = TypeEnum.BIOSAMPLE + "_" + TypeEnum.EXPERIMENT;

            var isStarted = false;
            var startTag  = XmlTagEnum.BIO_SAMPLE_START.getItem();
            var endTag    = XmlTagEnum.BIO_SAMPLE_END.getItem();
            while((line = br.readLine()) != null) {
                // XMLファイルの1,2行目は判定対象外
                if (line.matches("^(<\\?xml ).*") || line.matches("<BioSampleSet>")) {
                    continue;
                }

                // 開始要素を判断する
                if (line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入ってくる可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if (line.contains(endTag)) {
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    BioSample properties = this.getProperties(json, xmlPath);

                    if (null == properties) {
                        log.debug("Skip this metadata.");
                        continue;
                    }

                    var biosample = properties.getBioSample();

                    // accesion取得
                    var ids = biosample.getIDS();
                    var idlst = ids.getID();
                    String identifier = null;
                    for (SampleId id : idlst) {
                        if (!"BioSample".equals(id.getDB())) {
                            continue;
                        }
                        identifier = id.getContent();
                    }

                    // Title取得
                    var descriptions = biosample.getDescription();
                    var title = descriptions.getTitle();

                    // Description 取得
                    // FIXME 取得するパラグラフの値の選定方法(list.get(0)でいいか)
                    var comment = descriptions.getComment();
                    String description = null;
                    if (null != comment) {
                        description = null != comment.getParagraph() ? null : comment.getParagraph().get(0);
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
                    }

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
                }

                // 巨大ファイル対応
                var maximumRecord = config.other.maximumRecord;
                if (jsonList.size() >= maximumRecord || !br.ready()) {
                    BulkHelper.extract(jsonList, maximumRecord, _jsonList -> {
                        searchModule.bulkInsert(TypeEnum.BIOSAMPLE.getType(), _jsonList);
                    });
                    jsonList.clear();
                    jsonList = new ArrayList<>();
                }
            }
            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);
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

            log.debug("Converting json to bean is failed:" + "\t" + xmlPath + "\t" + message + "\t" + json);

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
