package com.ddbj.ld.app.transact.service.biosample;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.FileModule;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.external.ExternalBioSampleDao;
import com.ddbj.ld.app.transact.dao.primary.biosample.BioSampleDao;
import com.ddbj.ld.app.transact.dao.primary.biosample.DDBJBioSampleDao;
import com.ddbj.ld.app.transact.dao.primary.biosample.DDBJBioSampleDateDao;
import com.ddbj.ld.app.transact.dao.primary.common.SuppressedMetadataDao;
import com.ddbj.ld.app.transact.dao.primary.sra.DRAAccessionDao;
import com.ddbj.ld.app.transact.dao.primary.sra.SRARunDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.biosample.*;
import com.ddbj.ld.data.beans.common.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
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

    private final ConfigSet config;
    private final SimpleDateFormat esSimpleDateFormat;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;
    private final MessageModule messageModule;
    private final FileModule fileModule;

    private final SRARunDao runDao;
    private final SuppressedMetadataDao suppressedMetadataDao;
    private final BioSampleDao bioSampleDao;
    private final ExternalBioSampleDao exBioSampleDao;
    private final DDBJBioSampleDao ddbjBioSampleDao;
    private final DDBJBioSampleDateDao ddbjBioSampleDateDao;
    private final DRAAccessionDao draAccessionDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    private final String startTag = XmlTagEnum.BIOSAMPLE.start;
    private final String endTag = XmlTagEnum.BIOSAMPLE.end;

    public void registerNCBI() {

        var path = this.config.file.path.bioSample.ncbi;
        this.split(path);
        var outDir = new File(this.config.file.path.outDir);

        // ファイルごとにエラー情報を分けたいため、初期化
        this.errorInfo = new HashMap<>();

        var ddbjPrefix = "PRJD";
        var maximumRecord = this.config.search.maximumRecord;

        // 固定値
        // メタデータの種別、ElasticsearchのIndex名にも使用する
        var type = TypeEnum.BIOSAMPLE.type;

        // FIXME suppressedMetadataは将来的に色んなメタデータに対応するため、条件を変更する
        this.suppressedMetadataDao.dropIndex();
        this.suppressedMetadataDao.deleteAll();
        this.bioSampleDao.dropIndex();
        this.bioSampleDao.deleteAll();

        if(this.searchModule.existsIndex(type)) {
            var NCBIQuery = QueryBuilders.regexpQuery("identifier", "SAMN.*").rewrite("constant_score").caseInsensitive(true);
            var EBIQuery = QueryBuilders.regexpQuery("identifier", "SAME.*").rewrite("constant_score").caseInsensitive(true);
            var NCBIRequest = new DeleteByQueryRequest(type).setQuery(NCBIQuery);
            var EBIRequest = new DeleteByQueryRequest(type).setQuery(EBIQuery);
            this.searchModule.deleteByQuery(NCBIRequest);
            this.searchModule.deleteByQuery(EBIRequest);
        }

        for(var file : Objects.requireNonNull(outDir.listFiles())) {
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
                        var json = this.jsonModule.xmlToJson(sb.toString());

                        var bean = this.getBean(json, path);

                        if(null == bean) {
                            // errorInfoへの格納は上述のgetBeanから呼び出されるgetProperties内で行っているため、行わない

                            log.warn("Converting json to bean.:{}", json);

                            continue;
                        }

                        var identifier = bean.getIdentifier();

                        if(null == identifier || identifier.isBlank()) {
                            log.warn("Metadata doesn't have identifier.:{}", json);

                            List<String> values;
                            var key = "Metadata doesn't have identifier";

                            if(null == (values = this.errorInfo.get(key))) {
                                values = new ArrayList<>();
                            }

                            values.add(json);

                            this.errorInfo.put(key, values);

                            continue;
                        }

                        if(identifier.startsWith(ddbjPrefix)) {
                            continue;
                        }

                        if(StatusEnum.PUBLIC.status.equals(bean.getStatus())) {
                            var indexRequest = this.jsonModule.getIndexRequest(bean);

                            if(null == indexRequest) {
                                log.warn("Converting json to index requests.:{}", json);

                                continue;
                            }

                            requests.add(indexRequest);

                        } else if(StatusEnum.SUPPRESSED.status.equals(bean.getStatus())) {

                            var record = new Object[] {
                                    identifier,
                                    type,
                                    this.jsonModule.beanToJson(bean),
                            };
                            suppressedRecords.add(record);
                        }

                        recordList.add(new Object[] {
                                identifier,
                                bean.getStatus(),
                                bean.getVisibility(),
                                null == bean.getDateCreated() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDateCreated()).getTime()),
                                null == bean.getDateModified() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDateModified()).getTime()),
                                null == bean.getDatePublished() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDatePublished()).getTime()),
                                json
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

        }
    }

    public void registerDDBJ() {

        var target = this.config.file.path.bioSample.ddbj;
        var dist = this.config.file.path.outDir + "/ddbj_biosample_set.xml";

        this.fileModule.extractGZIP(target, dist);

        // ファイルごとにエラー情報を分けたいため、初期化
        this.errorInfo = new HashMap<>();

        var maximumRecord = this.config.search.maximumRecord;

        // 固定値
        // メタデータの種別、ElasticsearchのIndex名にも使用する
        var type = TypeEnum.BIOSAMPLE.type;

        this.ddbjBioSampleDao.dropIndex();
        this.ddbjBioSampleDao.deleteAll();
        this.ddbjBioSampleDateDao.deleteAll();

        if(this.searchModule.existsIndex(type)) {
            var query = QueryBuilders.regexpQuery("identifier", "SAMD.*").rewrite("constant_score").caseInsensitive(true);
            var request = new DeleteByQueryRequest(type).setQuery(query);
            this.searchModule.deleteByQuery(request);
        }

        try (var br = new BufferedReader(new FileReader(dist))) {
            String line;
            var sb = new StringBuilder();
            // Elasticsearch登録用
            var requests     = new BulkRequest();
            // Postgres登録用
            var recordList = new ArrayList<Object[]>();

            // DDBJ出力分XMLにはNCBIとは違いsuppressedが存在しないため登録しない

            var isStarted = false;

            // 日付情報を最初にまとめて取得しておく
            var dateList = this.exBioSampleDao.all();
            var dateRecordList = new ArrayList<Object[]>();

            for(var date : dateList) {
                dateRecordList.add(new Object[] {
                    date.getAccession(),
                    date.getDateCreated(),
                    date.getDatePublished(),
                    date.getDateModified()
                });

                if (dateRecordList.size() == maximumRecord) {
                    this.ddbjBioSampleDateDao.bulkInsert(dateRecordList);
                    dateRecordList = new ArrayList<>();
                }
            }

            if (dateRecordList.size() > 0) {
                this.ddbjBioSampleDateDao.bulkInsert(dateRecordList);
            }

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
                    var json = this.jsonModule.xmlToJson(sb.toString());

                    var bean = this.getBean(json, dist);

                    if(null == bean) {
                        // errorInfoへの格納は上述のgetBeanから呼び出されるgetProperties内で行っているため、行わない

                        log.warn("Converting json to bean.:{}", json);

                        continue;
                    }

                    var identifier = bean.getIdentifier();

                    if(null == identifier || identifier.isBlank()) {
                        log.warn("Metadata doesn't have identifier.:{}", json);

                        List<String> values;
                        var key = "Metadata doesn't have identifier";

                        if(null == (values = this.errorInfo.get(key))) {
                            values = new ArrayList<>();
                        }

                        values.add(json);

                        this.errorInfo.put(key, values);

                        continue;
                    }

                    var indexRequest = this.jsonModule.getIndexRequest(bean);

                    if(null == indexRequest) {
                        log.warn("Converting json to index requests.:{}", json);

                        continue;
                    }

                    requests.add(indexRequest);

                    recordList.add(new Object[] {
                            identifier,
                            bean.getStatus(),
                            bean.getVisibility(),
                            null == bean.getDateCreated() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDateCreated()).getTime()),
                            null == bean.getDateModified() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDateModified()).getTime()),
                            null == bean.getDatePublished() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDatePublished()).getTime()),
                            this.jsonModule.beanToJson(bean)
                    });

                    if(requests.numberOfActions() == maximumRecord) {
                        this.searchModule.bulkInsert(requests);
                        requests = new BulkRequest();
                    }

                    if(recordList.size() == this.config.other.maximumRecord) {
                        this.ddbjBioSampleDao.bulkInsert(recordList);
                        recordList = new ArrayList<>();
                    }
                }
            }

            if(requests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(requests);
            }

            if(recordList.size() > 0) {
                this.ddbjBioSampleDao.bulkInsert(recordList);
            }

            this.ddbjBioSampleDao.createIndex();
            this.fileModule.delete(dist);

        } catch (IOException | ParseException e) {
            var message = String.format("Not exists file:%s", dist);
            log.error(message, e);

            throw new DdbjException(message);
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
                    var json = this.jsonModule.xmlToJson(sb.toString());

                    // Json文字列をバリデーションにかけてから、Beanに変換する
                    this.getProperties(json, path);
                }
            }

            if(this.errorInfo.size() > 0) {
                this.messageModule.noticeErrorInfo(TypeEnum.BIOSAMPLE.type, this.errorInfo);

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

    public void createUpdatedData(final String date) {

        if(null == date) {
            var message = "Date is null.";
            log.error(message);

            throw new DdbjException(message);
        }

        this.bioSampleDao.createTempTable(date);

        var path = this.config.file.path.bioSample.ncbi;

        this.split(path);
        var outDir = new File(this.config.file.path.outDir);

        // ファイルごとにエラー情報を分けたいため、初期化
        this.errorInfo = new HashMap<>();

        var bioSampleNameSpace = "BioSample";
        var ddbjPrefix = "SAMD";

        for(var file : Objects.requireNonNull(outDir.listFiles())) {
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
                        var json = this.jsonModule.xmlToJson(sb.toString());

                        // Json文字列を項目取得用、バリデーション用にBean化する
                        // Beanにない項目がある場合はエラーを出力する
                        var properties = this.getProperties(json, path);

                        if(null == properties) {
                            log.warn("Converting json to bean.:{}", json);

                            continue;
                        }

                        // accesion取得
                        var ids = properties.getIds();

                        if(null == ids) {
                            continue;
                        }

                        var idlst = ids.getId();
                        String identifier = null;

                        for (var id : idlst) {
                            // DDBJ出力分、NCBI出力分で属性名が異なるため、この条件
                            if (bioSampleNameSpace.equals(id.getNamespace())
                                    || bioSampleNameSpace.equals(id.getDb())
                            ) {
                                identifier = id.getContent();
                            }
                        }

                        if(null == identifier || identifier.isBlank()) {
                            log.warn("Metadata doesn't have identifier.:{}", json);

                            List<String> values;
                            var key = "Metadata doesn't have identifier";

                            if(null == (values = this.errorInfo.get(key))) {
                                values = new ArrayList<>();
                            }

                            values.add(json);

                            this.errorInfo.put(key, values);

                            continue;
                        }

                        // DDBJのアクセッションはスキップ
                        if(identifier.startsWith(ddbjPrefix)) {
                            continue;
                        }

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
                                json
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
    }

    public void update(final String date) {
        if(null == date) {
            var message = "Date is null.";
            log.error(message);

            throw new DdbjException(message);
        }

        var newRecords = this.bioSampleDao.selNewRecord(date);
        var suppressedToPublicRecords = this.bioSampleDao.selSuppressedToPublic(date);
        var suppressedToUnpublishedRecords = this.bioSampleDao.selSuppressedToUnpublished(date);
        var publicToSuppressedRecords = this.bioSampleDao.selPublicToSuppressed(date);
        var publicToUnpublishedRecords = this.bioSampleDao.selPublicToUnpublished(date);
        var updatedRecords = this.bioSampleDao.selUpdatedRecord(date);

        var requests = new BulkRequest();
        var deleteFromSuppressedRecords = new ArrayList<Object[]>();
        var registerToSuppressedRecords = new ArrayList<Object[]>();
        var maximumRecord = this.config.other.maximumRecord;
        // メタデータの種別、ElasticsearchのIndex名にも使用する
        var type = TypeEnum.BIOSAMPLE.type;

        for(var record : newRecords) {
            var json = record.getJson();
            var bean = this.getBean(json);

            var indexRequest = this.jsonModule.getIndexRequest(bean);

            if(null == indexRequest) {
                log.warn("Converting json to index requests.:{}", json);

                continue;
            }

            requests.add(indexRequest);

            if(requests.numberOfActions() == maximumRecord) {
                this.searchModule.bulkInsert(requests);
                requests = new BulkRequest();
            }
        }

        for(var record : suppressedToPublicRecords) {
            var json = record.getJson();
            var bean = this.getBean(json);
            var identifier = bean.getIdentifier();

            var indexRequest = this.jsonModule.getIndexRequest(bean);

            if(null == indexRequest) {
                log.warn("Converting json to index requests.:{}", json);

                continue;
            }

            requests.add(indexRequest);

            deleteFromSuppressedRecords.add(new Object[] {
                    identifier
            });

            if(deleteFromSuppressedRecords.size() == maximumRecord) {
                this.suppressedMetadataDao.bulkDelete(deleteFromSuppressedRecords);
                deleteFromSuppressedRecords = new ArrayList<>();
            }

            if(requests.numberOfActions() == maximumRecord) {
                this.searchModule.bulkInsert(requests);
                requests = new BulkRequest();
            }
        }

        for(var record : suppressedToUnpublishedRecords) {

            deleteFromSuppressedRecords.add(new Object[] {
                    record.getAccession()
            });

            if(deleteFromSuppressedRecords.size() == maximumRecord) {
                this.suppressedMetadataDao.bulkDelete(deleteFromSuppressedRecords);
                deleteFromSuppressedRecords = new ArrayList<>();
            }
        }

        for(var record : publicToSuppressedRecords) {
            var json = record.getJson();
            var bean = this.getBean(json);
            var identifier = bean.getIdentifier();

            var deleteRequest = this.jsonModule.getDeleteRequest(type, identifier);

            if(null == deleteRequest) {
                log.warn("Converting json to delete requests.:{}", json);

                continue;
            }

            requests.add(deleteRequest);

            registerToSuppressedRecords.add(new Object[] {
                    identifier,
                    type,
                    this.jsonModule.beanToJson(bean),
            });

            if(registerToSuppressedRecords.size() == maximumRecord) {
                this.suppressedMetadataDao.bulkInsert(registerToSuppressedRecords);
                registerToSuppressedRecords = new ArrayList<>();
            }

            if(requests.numberOfActions() == maximumRecord) {
                this.searchModule.bulkInsert(requests);
                requests = new BulkRequest();
            }
        }

        for(var record : publicToUnpublishedRecords) {
            var json = record.getJson();
            var bean = this.getBean(json);
            var identifier = bean.getIdentifier();

            var deleteRequest = this.jsonModule.getDeleteRequest(type, identifier);

            if(null == deleteRequest) {
                log.warn("Converting json to delete requests.:{}", json);

                continue;
            }

            if(requests.numberOfActions() == maximumRecord) {
                this.searchModule.bulkInsert(requests);
                requests = new BulkRequest();
            }
        }

        for(var record : updatedRecords) {
            var json = record.getJson();
            var bean = this.getBean(json);

            var updateRequest = this.jsonModule.getUpdateRequest(bean);

            if(null == updateRequest) {
                log.warn("Converting json to update requests.:{}", json);

                continue;
            }

            requests.add(updateRequest);

            if(requests.numberOfActions() == maximumRecord) {
                this.searchModule.bulkInsert(requests);
                requests = new BulkRequest();
            }
        }

        if(deleteFromSuppressedRecords.size() > 0) {
            this.suppressedMetadataDao.bulkDelete(deleteFromSuppressedRecords);
        }

        if(registerToSuppressedRecords.size() > 0) {
            this.suppressedMetadataDao.bulkInsert(registerToSuppressedRecords);
        }

        if(requests.numberOfActions() > 0) {
            this.searchModule.bulkInsert(requests);
        }

        // 更新が完了したら一時テーブルを本テーブルにする
        this.bioSampleDao.drop();
        this.bioSampleDao.rename(date);
        this.bioSampleDao.renameIndex(date);

        if(this.errorInfo.size() > 0) {
            this.messageModule.noticeErrorInfo(TypeEnum.BIOSAMPLE.type, this.errorInfo);

        }
    }

    public void createUpdatedDDBJData(final String date) {
        // TODO
    }

    public void updateDDBJ(final String date) {
        // TODO
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
            var bean = BioSampleConverter.fromJsonString(json);

            return null == bean ? null : bean.getBiosample();
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

    private JsonBean getBean(
            final String json,
            final String path
    ) {
        var accessionPrefix = "PRJ";

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

        // メタデータダウンロードリンク用のファイル名とURL
        var ddbjFileName = "ddbj_biosample_set.xml.gz";
        var ncbiFileName = "biosample_set.xml.gz";
        var ddbjHttpsUrl = "https://ddbj.nig.ac.jp/public/ddbj_database/biosample/" + ddbjFileName;
        var ncbiHttpsUrl = "https://ddbj.nig.ac.jp/public/ddbj_database/biosample/" + ncbiFileName;
        var ddbjFtpUrl = "ftp://ftp.ddbj.nig.ac.jp/ddbj_database/biosample/" + ddbjFileName;
        var ncbiFtpUrl = "ftp://ftp.ddbj.nig.ac.jp/ddbj_database/biosample/" + ncbiFileName;

        // Json文字列を項目取得用、バリデーション用にBean化する
        // Beanにない項目がある場合はエラーを出力する
        var properties = this.getProperties(json, path);

        if (null == properties) {
            return null;
        }

        // accesion取得
        var ids = properties.getIds();

        if(null == ids) {
            return null;
        }

        var idlst = ids.getId();
        String identifier = null;
        List<SameAsBean> sameAs = null;
        var sampleDbXrefs = new ArrayList<DBXrefsBean>();
        // 重複チェック用
        var duplicatedCheck = new HashSet<String>();

        for (var id : idlst) {
            // DDBJ出力分、NCBI出力分で属性名が異なるため、この条件
            if (bioSampleNameSpace.equals(id.getNamespace())
                    || bioSampleNameSpace.equals(id.getDb())
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

        if(null == identifier || identifier.isBlank()) {
            log.error("Can't get identifier: {}", json);
            return null;
        }

        var isDDBJ = identifier.startsWith("SAMD");

        // Title取得
        var descriptions = properties.getDescription();
        var title = descriptions.getTitle();

        // Description 取得
        var comment = descriptions.getComment();
        // Paragraphは最小値が1のはずだが0のものもあり、例外で落ちることがあるためcomment.getParagraph().size() == 0の条件を追加
        var description = null == comment || null == comment.getParagraph() || comment.getParagraph().size() == 0 ? null : comment.getParagraph().get(0);

        // name 取得
        var attributes = properties.getAttributes();
        var attributeList = null == attributes ? null : attributes.getAttribute();
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
        List<AccessionsBean> runList;

        if (isDDBJ) {
            runList = this.draAccessionDao.selRunByBioSample(identifier);
        } else {
            runList = this.runDao.selByBioSample(identifier);
        }

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

        var dbXrefsStatistics = new ArrayList<DBXrefsStatisticsBean>();
        var statisticsMap = new HashMap<String, Integer>();

        for(var dbXref : dbXrefs) {
            var dbXrefType = dbXref.getType();
            var count = null == statisticsMap.get(dbXrefType) ? 1 : statisticsMap.get(dbXrefType) + 1;

            statisticsMap.put(dbXrefType, count);
        }

        for (var entry : statisticsMap.entrySet()) {
            dbXrefsStatistics.add(new DBXrefsStatisticsBean(
                    entry.getKey(),
                    entry.getValue()
            ));
        }

        // Biosampleには<Status status="live" when="2012-11-01T11:46:11.057"/>といったようにstatusが存在するため、それを参照にする
        var propStatus = null == properties.getStatus() || null == properties.getStatus().getStatus() ? null : properties.getStatus().getStatus();
        String status;

        if(StatusEnum.SUPPRESSED.status.equals(propStatus)) {
            status = StatusEnum.SUPPRESSED.status;
        } else {
            // それ以外はリソース統合ではpublicとして扱う
            status = StatusEnum.PUBLIC.status;
        }

        // Biosampleには<BioSample access="controlled-access"といったようにaccessが存在するため、それを参照にする
        // public、もしくは指定されていない場合はunrestricted-accessとする
        var visibility = "public".equals(properties.getAccess()) || null == properties.getAccess() ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : VisibilityEnum.CONTROLLED_ACCESS.visibility;

        var search = this.jsonModule.beanToJson(properties);

        var distribution = this.jsonModule.getDistribution(TypeEnum.BIOSAMPLE.getType(), identifier);
        List<DownloadUrlBean> downloadUrl = new ArrayList<>();

        String dateCreated;
        String dateModified;
        String datePublished;

        if (isDDBJ) {
            downloadUrl.add(new DownloadUrlBean(
                    "meta",
                    ddbjFileName,
                    ddbjHttpsUrl,
                    ddbjFtpUrl
            ));

            var date = this.ddbjBioSampleDateDao.select(identifier);
            datePublished = this.jsonModule.parseLocalDateTimeByJST(null == date ? null : date.getDatePublished());
            dateCreated = this.jsonModule.parseLocalDateTimeByJST(null == date ? null : date.getDateCreated());
            dateModified = this.jsonModule.parseLocalDateTimeByJST(null == date ? null : date.getDateModified());
        } else {
            downloadUrl.add(new DownloadUrlBean(
                    "meta",
                    ncbiFileName,
                    ncbiHttpsUrl,
                    ncbiFtpUrl
            ));

            datePublished = this.jsonModule.parseOffsetDateTime(properties.getPublicationDate());
            dateCreated   = null == properties.getSubmissionDate() ? datePublished : this.jsonModule.parseOffsetDateTime(properties.getSubmissionDate());
            dateModified  = null == properties.getSubmissionDate() ? datePublished : this.jsonModule.parseOffsetDateTime(properties.getLastUpdate());
        }

        return new JsonBean(
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
                dbXrefsStatistics,
                properties,
                search,
                distribution,
                downloadUrl,
                status,
                visibility,
                dateCreated,
                dateModified,
                datePublished
        );
    }

    private JsonBean getBean(final String json) {
        return this.getBean(json, null);
    }
}
