package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.bioproject.BioProjectBioSampleDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.data.beans.biosample.*;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BioSampleService {

    private final ObjectMapper objectMapper;

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;

    private final BioProjectBioSampleDao bioProjectBioSampleDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    private String startTag = XmlTagEnum.BIO_SAMPLE.start;
    private String endTag = XmlTagEnum.BIO_SAMPLE.end;

    public void register(
        final String path,
        final CenterEnum center,
        final boolean deletable
    ) {
        this.split(path);
        var outDir = new File(this.config.file.bioSample.outDir);

        // ファイルごとにエラー情報を分けたいため、初期化
        this.errorInfo = new HashMap<>();

        var ddbjPrefix = "PRJD";
        var maximumRecord = this.config.other.maximumRecord;
        var cnt = 0;

        // 固定値
        var status = StatusEnum.LIVE.status;
        var visibility = VisibilityEnum.PUBLIC.visibility;
        // メタデータの種別、ElasticsearchのIndex名にも使用する
        var type = TypeEnum.BIOSAMPLE.type;
        var isPartOf = IsPartOfEnum.BIOPSAMPLE.isPartOf;

        if(this.searchModule.existsIndex(type) && deletable) {
            this.searchModule.deleteIndex(type);
        }

        for(var file : outDir.listFiles()) {
            try (var br = new BufferedReader(new FileReader(file))) {
                String line;
                var sb = new StringBuilder();
                var requests = new BulkRequest();

                var isStarted = false;

                while((line = br.readLine()) != null) {
                    // 開始要素を判断する
                    if (line.contains(this.startTag)) {
                        isStarted = true;
                        sb = new StringBuilder();
                    }

                    if(isStarted) {
                        sb.append(line);
                    }

                    // 2つ以上入ってくる可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                    if (line.contains(this.endTag)) {
                        var json = XML.toJSONObject(sb.toString()).toString();

                        // Json文字列を項目取得用、バリデーション用にBean化する
                        // Beanにない項目がある場合はエラーを出力する
                        var properties = this.getProperties(json, path);

                        if (null == properties) {
                            continue;
                        }

                        // accesion取得
                        var ids = properties.getIDS();
                        var idlst = ids.getID();
                        String identifier = null;
                        for (SampleId id : idlst) {
                            if ("BioSample".equals(id.getNamespace())
                             || "BioSample".equals(id.getDB())
                            ) {
                                identifier = id.getContent();
                                break;
                            }
                        }

                        // 他局出力のファイルならDDBJのアクセッションはスキップ
                        if(center != CenterEnum.DDBJ
                                && identifier.startsWith(ddbjPrefix)) {
                            continue;
                        }

                        // Title取得
                        var descriptions = properties.getDescription();
                        var title = descriptions.getTitle();

                        // Description 取得
                        var comment = descriptions.getComment();
                        String description = null == comment || null == comment.getParagraph() ? null : comment.getParagraph().get(0);

                        // name 取得
                        var attributes = properties.getAttributes();
                        var attributeList = attributes.getAttribute();
                        String name = null;
                        for (Attribute attribute : attributeList) {
                            if ("sample_name".equals(attribute.getHarmonizedName())
                             || "sample_name".equals(attribute.getAttributeName())) {
                                name = attribute.getContent();
                                break;
                            }
                        }

                        // bioproject/SAMN???????
                        var url = this.jsonModule.getUrl(type, identifier);

                        // 生物名とIDはSampleのみの情報であるため空情報を設定
                        var organisms = descriptions.getOrganism().get(0);

                        // 生物名とIDを設定
                        var organismName = null == organisms.getOrganismName() ? null :  organisms.getOrganismName();
                        var organismIdentifier = organisms.getTaxonomyID();

                        var organism = this.jsonModule.getOrganism(organismName, organismIdentifier);

                        List<DBXrefsBean> dbXrefs = new ArrayList<>();
                        var bioProjectList = this.bioProjectBioSampleDao.selBioProject(identifier);
                        dbXrefs.addAll(bioProjectList);

                        // 自分と同値(Sample)の情報を保持するデータを指定
                        List<SameAsBean> sameAs = null;
                        for (SampleId id : idlst) {
                            if ("SRA".equals(id.getNamespace())) {
                                sameAs = new ArrayList<>();

                                var sampleId = id.getContent();
                                var sampleType = TypeEnum.SAMPLE.type;
                                var sampleUrl = this.jsonModule.getUrl(type, sampleId);

                                var item = new SameAsBean();
                                var sameAsId = sampleId;
                                var sameAsType = sampleType;
                                var sameAsUrl = url;
                                item.setIdentifier(sameAsId);
                                item.setType(sameAsType);
                                item.setUrl(sameAsUrl);
                                sameAs.add(item);

                                var sampleDbXrefs = new DBXrefsBean(
                                        sampleId,
                                        sampleType,
                                        sampleUrl
                                );

                                dbXrefs.add(sampleDbXrefs);

                                break;
                            }
                        }

                        var distribution = this.jsonModule.getDistribution(TypeEnum.BIOSAMPLE.getType(), identifier);

                        var datePublished = this.jsonModule.parseOffsetDateTime(properties.getPublicationDate());
                        var dateCreated   = null == properties.getSubmissionDate() ? datePublished : this.jsonModule.parseOffsetDateTime(properties.getSubmissionDate());
                        var dateModified  = null == properties.getSubmissionDate() ? datePublished : this.jsonModule.parseOffsetDateTime(properties.getLastUpdate());

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

                        requests.add(new IndexRequest(type).id(identifier).source(this.objectMapper.writeValueAsString(bean), XContentType.JSON));

                        cnt++;

                        if(requests.numberOfActions() == maximumRecord) {
                            this.searchModule.bulkInsert(requests);
                            requests = new BulkRequest();
                        }

                        // 経過観察のために最大処理件数の10倍ごとにログ出力
                        if(0 < cnt && 0 == (cnt % (maximumRecord * 10))) {
                            log.info("processed biosample record:{}", cnt);
                        }
                    }
                }

                if(requests.numberOfActions() > 0) {
                    this.searchModule.bulkInsert(requests);
                }

                log.info("processed biosample record:{}", cnt);

            } catch (IOException e) {
                log.error("Not exists file:{}", path, e);
            }
        }

        this.remove();
    }

    private void split(final String path) {

        try(var reader = new BufferedReader(new FileReader(path))) {
            var setStartTag = "<BioSampleSet>\n";
            var setEndTag = "</BioSampleSet>";

            var isStarted = false;
            var recordSize = 0;
            var fileNo = 1;

            var xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            String line;

            var sb = new StringBuilder();
            sb.append(xmlHeader);
            sb.append(setStartTag);

            var outDir = this.config.file.bioSample.outDir;

            if(!Files.exists(Paths.get(outDir))) {
                Files.createDirectory(Paths.get(outDir));
            }

            while ((line = reader.readLine()) != null) {
                if (line.contains(this.startTag)) {
                    isStarted = true;
                }
                if (isStarted && reader.ready()) {
                    sb.append("  ");
                    sb.append(line);
                    sb.append("\n");
                }

                if (line.contains(this.endTag)) {
                    recordSize++;
                }

                // 最大10万レコード単位でファイルを分けて出力する
                if (recordSize == 100000 || !reader.ready()) {
                    sb.append(setEndTag);

                    var fsb = new StringBuilder(outDir);
                    fsb.append("/");
                    fsb.append("biosample_set");
                    fsb.append(fileNo);
                    fsb.append(".xml");

                    try (var bw = Files.newBufferedWriter(Paths.get(fsb.toString()));
                        var pw = new PrintWriter(bw, true)) {
                        pw.println(sb.toString());
                    }

                    // 初期化して次のファイル出力へ
                    recordSize = 0;
                    sb = new StringBuilder(xmlHeader);
                    sb.append(setStartTag);

                    fileNo++;
                }
            }
        } catch (IOException e) {
            log.error("Splitting file is failed.", e);
        }
    }

    private void remove() {
        var outDir = this.config.file.bioSample.outDir;

        try {
            var fileList = new File(outDir);

            for(var file : fileList.listFiles()) {
                Files.delete(file.toPath());
            }

            Files.delete(Paths.get(outDir));

        } catch (IOException e) {
            log.error("Removing directory failed.", e);
        }
    }

    private BioSampleClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = Converter.fromJsonString(json);

            return bean.getBioSample();
        } catch (IOException e) {
            var message = e.getLocalizedMessage()
                    .replaceAll("\n at.*.", "")
                    .replaceAll("\\(.*.", "");

            log.debug("Converting json to bean is failed:" + "\t" + path + "\t" + message + "\t" + json);

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
