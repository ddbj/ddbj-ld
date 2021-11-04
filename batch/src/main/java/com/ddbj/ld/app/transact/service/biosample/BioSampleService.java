package com.ddbj.ld.app.transact.service.biosample;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.FileModule;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.biosample.BioSampleDao;
import com.ddbj.ld.app.transact.dao.common.SuppressedMetadataDao;
import com.ddbj.ld.app.transact.dao.sra.SraRunDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BioSampleService {

    private final ObjectMapper objectMapper;

    private final ConfigSet config;
    private final SimpleDateFormat esSimpleDateFormat;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;
    private final MessageModule messageModule;
    private final FileModule fileModule;

    private final SraRunDao runDao;
    private final SuppressedMetadataDao suppressedMetadataDao;
    private final BioSampleDao bioSampleDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    private String startTag = XmlTagEnum.BIOSAMPLE.start;
    private String endTag = XmlTagEnum.BIOSAMPLE.end;

    public void delete() {
        if(this.searchModule.existsIndex(TypeEnum.BIOSAMPLE.type)) {
            this.searchModule.deleteIndex(TypeEnum.BIOSAMPLE.type);
        }
    }

    public void register(
        final String path,
        final CenterEnum center
    ) {
        this.split(path);
        var outDir = new File(this.config.file.path.outDir);

        // ファイルごとにエラー情報を分けたいため、初期化
        this.errorInfo = new HashMap<>();

        var accessionPrefix = "PRJ";
        var ddbjPrefix = "PRJD";
        var maximumRecord = this.config.search.maximumRecord;

        // 固定値
        // メタデータの種別、ElasticsearchのIndex名にも使用する
        var type = TypeEnum.BIOSAMPLE.type;
        var isPartOf = IsPartOfEnum.BIOPSAMPLE.isPartOf;
        var sraType = "SRA";
        var bioSampleNameSpace = "BioSample";
        var sampleAttributeName = "sample_name";

        // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
        var bioProjectType = TypeEnum.BIOPROJECT.type;
        var submissionType = TypeEnum.SUBMISSION.type;
        var experimentType = TypeEnum.EXPERIMENT.type;
        var runType = TypeEnum.RUN.type;
        var studyType = TypeEnum.STUDY.type;
        var sampleType = TypeEnum.SAMPLE.type;

        this.suppressedMetadataDao.dropIndex();
        this.suppressedMetadataDao.deleteAll();
        this.bioSampleDao.dropIndex();
        this.bioSampleDao.deleteAll();

        for(var file : outDir.listFiles()) {
            try (var br = new BufferedReader(new FileReader(file))) {
                String line;
                var sb = new StringBuilder();
                // Elasticsearch登録用
                var requests     = new BulkRequest();
                // Postgres登録用
                var recordList = new ArrayList<Object[]>();
                var suppressedRecords = new ArrayList<Object[]>();

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

                        if(null == ids) {
                            continue;
                        }

                        var idlst = ids.getID();
                        String identifier = null;
                        List<SameAsBean> sameAs = null;
                        var sampleDbXrefs = new ArrayList<DBXrefsBean>();
                        // 重複チェック用
                        var duplicatedCheck = new HashSet<String>();

                        for (var id : idlst) {
                            // DDBJ出力分、NCBI出力分で属性名が異なるため、この条件
                            if (bioSampleNameSpace.equals(id.getNamespace())
                             || bioSampleNameSpace.equals(id.getDB())
                            ) {
                                identifier = id.getContent();
                            }

                            if (sraType.equals(id.getNamespace())) {
                                // 自分と同値(Sample)の情報を保持するデータを指定
                                sameAs = new ArrayList<>();

                                var sampleId = id.getContent();
                                var sampleUrl = this.jsonModule.getUrl(type, sampleId);

                                sameAs.add(new SameAsBean(sampleId, sampleType, sampleUrl));
                                sampleDbXrefs.add(new DBXrefsBean(sampleId, sampleType, sampleUrl));
                                duplicatedCheck.add(sampleId);
                            }
                        }

                        if(null == identifier) {
                            log.error("Can't get identifier: {}", json);
                            continue;
                        }

                        // 他局出力のファイルならDDBJのアクセッションはスキップ
                        // FIXME DDBJ出力分からの取り込みはファーストリリースからは外したため、一時的にコメントアウト
//                        if(center != CenterEnum.DDBJ
//                                && identifier.startsWith(ddbjPrefix)) {
//                            continue;
//                        }

                        // Title取得
                        var descriptions = properties.getDescription();
                        var title = descriptions.getTitle();

                        // Description 取得
                        var comment = descriptions.getComment();
                        // Paragraphは最小値が1のはずだが0のものもあり、例外で落ちることがあるためcomment.getParagraph().size() == 0の条件を追加
                        var description = null == comment || null == comment.getParagraph() || comment.getParagraph().size() == 0 ? null : comment.getParagraph().get(0);

                        // name 取得
                        var attributes = properties.getAttributes();
                        var attributeList = attributes.getAttribute();
                        String name = null;

                        if(null != attributeList) {
                            for (Attribute attribute : attributeList) {
                                // DDBJ出力分、NCBI出力分で属性名が異なるため、この条件
                                if (sampleAttributeName.equals(attribute.getHarmonizedName())
                                        || sampleAttributeName.equals(attribute.getAttributeName())) {
                                    name = attribute.getContent();
                                    break;
                                }
                            }
                        }

                        // biosample/SAMN???????
                        var url = this.jsonModule.getUrl(type, identifier);
                        var organisms = null == descriptions.getOrganism() ? null : descriptions.getOrganism().get(0);

                        // 生物名とIDを設定
                        var organismName = null == organisms ? null : organisms.getOrganismName();
                        var organismIdentifier = null == organisms ? null : organisms.getTaxonomyID();

                        var organism = this.jsonModule.getOrganism(organismName, organismIdentifier);

                        List<DBXrefsBean> dbXrefs = new ArrayList<>();

                        // bioproject、submission、experiment、study、runを取得
                        var runList = this.runDao.selByBioSample(identifier);

                        // analysisはbioproject, studyとしか紐付かないようで取得できない
                        var bioProjectDbXrefs = new ArrayList<DBXrefsBean>();
                        var submissionDbXrefs = new ArrayList<DBXrefsBean>();
                        var experimentDbXrefs = new ArrayList<DBXrefsBean>();
                        var runDbXrefs = new ArrayList<DBXrefsBean>();
                        var studyDbXrefs = new ArrayList<DBXrefsBean>();

                        for(var run : runList) {
                            var bioProjectId = run.getBioProject();
                            var submissionId = run.getSubmission();
                            var experimentId = run.getExperiment();
                            var runId = run.getAccession();
                            var studyId = run.getStudy();
                            var sampleId = run.getSample();

                            if(!duplicatedCheck.contains(bioProjectId)) {
                                bioProjectDbXrefs.add(this.jsonModule.getDBXrefs(bioProjectId, bioProjectType));
                                duplicatedCheck.add(bioProjectId);
                            }

                            if(!duplicatedCheck.contains(submissionId)) {
                                submissionDbXrefs.add(this.jsonModule.getDBXrefs(submissionId, submissionType));
                                duplicatedCheck.add(submissionId);
                            }

                            if(!duplicatedCheck.contains(experimentId)) {
                                experimentDbXrefs.add(this.jsonModule.getDBXrefs(experimentId, experimentType));
                                duplicatedCheck.add(experimentId);
                            }

                            if(!duplicatedCheck.contains(runId)) {
                                runDbXrefs.add(this.jsonModule.getDBXrefs(runId, runType));
                                duplicatedCheck.add(runId);
                            }

                            if(!duplicatedCheck.contains(studyId)) {
                                studyDbXrefs.add(this.jsonModule.getDBXrefs(studyId, studyType));
                                duplicatedCheck.add(studyId);
                            }

                            if(!duplicatedCheck.contains(sampleId)) {
                                // 上述の処理からでは取得できない場合があるのため、保険
                                sameAs = new ArrayList<>();
                                sameAs.add(new SameAsBean(sampleId, sampleType, this.jsonModule.getUrl(sampleType, sampleId)));

                                sampleDbXrefs.add(this.jsonModule.getDBXrefs(sampleId, sampleType));
                                duplicatedCheck.add(sampleId);
                            }
                        }

                        // Linkを使って更にBioProject
                        var linkList = null == properties.getLinks() ? null : properties.getLinks().getLink();

                        if(null != linkList) {
                            for(var link : linkList) {
                                // content == bioprojectのアクセッション
                                var content = link.getContent();

                                if(bioProjectType.equals(link.getTarget())
                                // 209492といったようにアクセッションとは違った形態の番号も混入してくるのでアクセッションのみを取得
                                && accessionPrefix.startsWith(content)
                                && !duplicatedCheck.contains(content)
                                ) {
                                    bioProjectDbXrefs.add(new DBXrefsBean(
                                            content,
                                            bioProjectType,
                                            this.jsonModule.getUrl(bioProjectType, content)
                                    ));
                                    duplicatedCheck.add(content);
                                }
                            }
                        }

                        // bioproject→submission→experiment→run→study→sampleの順でDbXrefsを格納していく
                        dbXrefs.addAll(bioProjectDbXrefs);
                        dbXrefs.addAll(submissionDbXrefs);
                        dbXrefs.addAll(experimentDbXrefs);
                        dbXrefs.addAll(runDbXrefs);
                        dbXrefs.addAll(studyDbXrefs);
                        dbXrefs.addAll(sampleDbXrefs);

                        // Biosampleには<Status status="live" when="2012-11-01T11:46:11.057"/>といったようにstatusが存在するため、それを参照にする
                        var propStatus = null == properties.getStatus() || null == properties.getStatus().getStatus() ? null : properties.getStatus().getStatus();
                        String status = "";

                        if(StatusEnum.LIVE.status.equals(propStatus)) {
                            // BioSample上ではliveだがリソース統合ではpublicとして扱う
                            status = StatusEnum.PUBLIC.status;
                        } else if(StatusEnum.SUPPRESSED.status.equals(propStatus)) {
                            status = StatusEnum.SUPPRESSED.status;
                        } else {
                            // それ以外ならログ出力してスキップ
                            log.warn("Record has illegal status: {}, json: {}", propStatus, json);
                        }

                        // Biosampleには<BioSample access="controlled-access"といったようにaccessが存在するため、それを参照にする
                        // publicはunrestricted-accessとする
                        var visibility = "public".equals(properties.getAccess()) ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : VisibilityEnum.CONTROLLED_ACCESS.visibility;

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

                        var jsonString = this.objectMapper.writeValueAsString(bean);

                        if(StatusEnum.PUBLIC.status.equals(bean.getStatus())) {
                            requests.add(new IndexRequest(type).id(identifier).source(jsonString, XContentType.JSON));
                        } else if(StatusEnum.SUPPRESSED.status.equals(bean.getStatus())) {
                            var record = new Object[] {
                                    identifier,
                                    type,
                                    jsonString,
                            };
                            suppressedRecords.add(record);
                        }

                        recordList.add(new Object[] {
                                identifier,
                                status,
                                visibility,
                                null == dateCreated ? null : new Timestamp(this.esSimpleDateFormat.parse(dateCreated).getTime()),
                                null == dateModified ? null : new Timestamp(this.esSimpleDateFormat.parse(dateModified).getTime()),
                                null == datePublished ? null : new Timestamp(this.esSimpleDateFormat.parse(datePublished).getTime()),
                                this.objectMapper.writeValueAsString(properties)
                        });

                        if(requests.numberOfActions() == maximumRecord) {
                            this.searchModule.bulkInsert(requests);
                            requests = new BulkRequest();
                        }

                        if(suppressedRecords.size() == this.config.other.maximumRecord) {
                            this.suppressedMetadataDao.bulkInsert(suppressedRecords);
                            suppressedRecords = new ArrayList<>();
                        }

                        if(recordList.size() == this.config.other.maximumRecord) {
                            this.bioSampleDao.bulkInsert(recordList);
                            recordList = new ArrayList<>();
                        }
                    }
                }

                if(requests.numberOfActions() > 0) {
                    this.searchModule.bulkInsert(requests);
                }

                if(suppressedRecords.size() > 0) {
                    this.suppressedMetadataDao.bulkInsert(suppressedRecords);
                }

                if(recordList.size() > 0) {
                    this.bioSampleDao.bulkInsert(recordList);
                }

            } catch (IOException | ParseException e) {
                var message = String.format("Not exists file:%s", path);
                log.error(message, e);

                throw new DdbjException(message);
            }
        }

        this.suppressedMetadataDao.createIndex();
        this.bioSampleDao.createIndex();

        for(Map.Entry<String, List<String>> entry : this.errorInfo.entrySet()) {
            // パース失敗したJsonの統計情報を出す
            var message = entry.getKey();
            var values  = entry.getValue();
            // パース失敗したサンプルのJsonを1つピックアップ
            var json    = values.get(0);
            var count   = values.size();

            log.error("Converting json to bean is failed:\t{}\t{}\t{}\t{}", message, count, path, json);
        }

        this.remove();

        if(this.errorInfo.size() > 0) {
            this.messageModule.noticeErrorInfo(TypeEnum.BIOSAMPLE.type, this.errorInfo);

        } else {
            var comment = String.format(
                    "%s\nbiosample register success.",
                    this.config.message.mention
            );

            this.messageModule.postMessage(this.config.message.channelId, comment);
        }
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

            var outDir = this.config.file.path.outDir;
            this.fileModule.createDirectory(outDir);

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
            var message = String.format("Splitting file is failed.:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void validate(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb  = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.BIOSAMPLE.start;
            var endTag    = XmlTagEnum.BIOSAMPLE.end;

            // ファイルごとにエラー情報を分けたいため、初期化
            this.errorInfo = new HashMap<>();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb        = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                if(line.contains(endTag)) {
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json文字列をバリデーションにかけてから、Beanに変換する
                    this.getProperties(json, path);
                }
            }

            if(this.errorInfo.size() > 0) {
                this.messageModule.noticeErrorInfo(TypeEnum.BIOSAMPLE.type, this.errorInfo);

            } else {
                var comment = String.format(
                        "%s\nbiosample validation success.",
                        this.config.message.mention
                );

                this.messageModule.postMessage(this.config.message.channelId, comment);
            }

        } catch (IOException e) {
            var message = String.format("Not exists file.:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void getMetadata() {
        var targetPath = "/biosample/biosample_set.xml.gz";
        var outPath = this.config.file.path.outDir + "/biosample_set.xml.gz";
        var targetDist = this.config.file.path.bioSample.ncbi;

        this.fileModule.delete(targetDist);
        log.info("Download {}.", targetPath);
        // ダウンロード
        this.fileModule.retrieveFile(this.config.file.ftp.ncbi, targetPath, outPath);
        log.info("Complete download {}.", targetPath);

        this.fileModule.extractGZIP(outPath, targetDist);
        this.fileModule.delete(outPath);
    }

    public void createUpdatedData(
            final String date,
            final String path,
            final CenterEnum center
            ) {
        this.bioSampleDao.createTempTable(date);

        this.split(path);
        var outDir = new File(this.config.file.path.outDir);

        // ファイルごとにエラー情報を分けたいため、初期化
        this.errorInfo = new HashMap<>();

        var bioSampleNameSpace = "BioSample";

        for(var file : outDir.listFiles()) {
            try (var br = new BufferedReader(new FileReader(file))) {
                String line;
                var sb = new StringBuilder();
                // Postgres登録用
                var recordList = new ArrayList<Object[]>();

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

                        if(null == ids) {
                            continue;
                        }

                        var idlst = ids.getID();
                        String identifier = null;

                        for (var id : idlst) {
                            // DDBJ出力分、NCBI出力分で属性名が異なるため、この条件
                            if (bioSampleNameSpace.equals(id.getNamespace())
                                    || bioSampleNameSpace.equals(id.getDB())
                            ) {
                                identifier = id.getContent();
                            }
                        }

                        if(null == identifier) {
                            log.error("Can't get identifier: {}", json);
                            continue;
                        }

                        // 他局出力のファイルならDDBJのアクセッションはスキップ
                        // FIXME DDBJ出力分からの取り込みはファーストリリースからは外したため、一時的にコメントアウト
//                        if(center != CenterEnum.DDBJ
//                                && identifier.startsWith(ddbjPrefix)) {
//                            continue;
//                        }

                        // Biosampleには<Status status="live" when="2012-11-01T11:46:11.057"/>といったようにstatusが存在するため、それを参照にする
                        var propStatus = null == properties.getStatus() || null == properties.getStatus().getStatus() ? null : properties.getStatus().getStatus();
                        String status = "";

                        if(StatusEnum.LIVE.status.equals(propStatus)) {
                            // BioSample上ではliveだがリソース統合ではpublicとして扱う
                            status = StatusEnum.PUBLIC.status;
                        } else if(StatusEnum.SUPPRESSED.status.equals(propStatus)) {
                            status = StatusEnum.SUPPRESSED.status;
                        } else {
                            // それ以外ならログ出力してスキップ
                            log.warn("Record has illegal status: {}, json: {}", propStatus, json);
                        }

                        // Biosampleには<BioSample access="controlled-access"といったようにaccessが存在するため、それを参照にする
                        // publicはunrestricted-accessとする
                        var visibility = "public".equals(properties.getAccess()) ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : VisibilityEnum.CONTROLLED_ACCESS.visibility;

                        var datePublished = this.jsonModule.parseOffsetDateTime(properties.getPublicationDate());
                        var dateCreated   = null == properties.getSubmissionDate() ? datePublished : this.jsonModule.parseOffsetDateTime(properties.getSubmissionDate());
                        var dateModified  = null == properties.getSubmissionDate() ? datePublished : this.jsonModule.parseOffsetDateTime(properties.getLastUpdate());

                        recordList.add(new Object[] {
                                identifier,
                                status,
                                visibility,
                                null == dateCreated ? null : new Timestamp(this.esSimpleDateFormat.parse(dateCreated).getTime()),
                                null == dateModified ? null : new Timestamp(this.esSimpleDateFormat.parse(dateModified).getTime()),
                                null == datePublished ? null : new Timestamp(this.esSimpleDateFormat.parse(datePublished).getTime()),
                                this.objectMapper.writeValueAsString(properties)
                        });

                        if(recordList.size() == this.config.other.maximumRecord) {
                            this.bioSampleDao.bulkInsertTemp(date, recordList);
                            recordList = new ArrayList<>();
                        }
                    }
                }

                if(recordList.size() > 0) {
                    this.bioSampleDao.bulkInsertTemp(date, recordList);
                }

            } catch (IOException | ParseException e) {
                this.bioSampleDao.dropTempTable(date);

                var message = String.format("Not exists file.:%s", path);
                log.error(message, e);

                throw new DdbjException(message);
            }
        }

        this.bioSampleDao.createTempIndex(date);

        for(Map.Entry<String, List<String>> entry : this.errorInfo.entrySet()) {
            // パース失敗したJsonの統計情報を出す
            var message = entry.getKey();
            var values  = entry.getValue();
            // パース失敗したサンプルのJsonを1つピックアップ
            var json    = values.get(0);
            var count   = values.size();

            log.error("Converting json to bean is failed:\t{}\t{}\t{}\t{}", message, count, path, json);
        }

        this.remove();

        if(this.errorInfo.size() > 0) {
            this.messageModule.noticeErrorInfo(TypeEnum.BIOSAMPLE.type, this.errorInfo);

        } else {
            var comment = String.format(
                    "%s\nCreating updated BioSample's table is success.",
                    this.config.message.mention
            );

            this.messageModule.postMessage(this.config.message.channelId, comment);
        }
    }

    private void remove() {
        var outDir = this.config.file.path.outDir;

        try {
            var fileList = new File(outDir);

            for(var file : fileList.listFiles()) {
                Files.delete(file.toPath());
            }

            Files.delete(Paths.get(outDir));

        } catch (IOException e) {
            var message = String.format("Removing directory failed.");
            log.error(message, e);

            throw new DdbjException(message);
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
