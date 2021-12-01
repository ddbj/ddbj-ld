package com.ddbj.ld.app.transact.service.bioproject;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.FileModule;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.external.ExternalBioProjectDao;
import com.ddbj.ld.app.transact.dao.primary.bioproject.BioProjectDao;
import com.ddbj.ld.app.transact.dao.primary.bioproject.DDBJBioProjectDao;
import com.ddbj.ld.app.transact.dao.primary.sra.DRAAccessionDao;
import com.ddbj.ld.app.transact.dao.primary.sra.SRAAnalysisDao;
import com.ddbj.ld.app.transact.dao.primary.sra.SRARunDao;
import com.ddbj.ld.app.transact.dao.primary.sra.SRASampleDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.bioproject.CenterID;
import com.ddbj.ld.data.beans.bioproject.Converter;
import com.ddbj.ld.data.beans.bioproject.Package;
import com.ddbj.ld.data.beans.common.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class BioProjectService {

    private final ConfigSet config;
    private final SimpleDateFormat esSimpleDateFormat;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;
    private final MessageModule messageModule;
    private final FileModule fileModule;

    private final SRARunDao runDao;
    private final SRAAnalysisDao analysisDao;
    private final SRASampleDao sampleDao;
    private final BioProjectDao bioProjectDao;
    private final ExternalBioProjectDao exBioProjectDao;
    private final DDBJBioProjectDao ddbjBioProjectDao;
    private final DRAAccessionDao draAccessionDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public void registerNCBI() {

        // NCBI, EBI由来のレコードを削除
        this.bioProjectDao.dropIndex();
        this.bioProjectDao.deleteAll();

        if (this.searchModule.existsIndex("bioproject")) {
            var NCBIQuery = QueryBuilders.regexpQuery("identifier", "PRJN.*").rewrite("constant_score").caseInsensitive(true);
            var EBIQuery = QueryBuilders.regexpQuery("identifier", "PRJE.*").rewrite("constant_score").caseInsensitive(true);
            var NCBIRequest = new DeleteByQueryRequest("bioproject").setQuery(NCBIQuery);
            var EBIRequest = new DeleteByQueryRequest("bioproject").setQuery(EBIQuery);
            this.searchModule.deleteByQuery(NCBIRequest);
            this.searchModule.deleteByQuery(EBIRequest);
        }

        var path = this.config.file.path.bioProject.ncbi;

        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            // Elasticsearch登録用
            var requests     = new BulkRequest();
            // Postgres登録用
            var recordList = new ArrayList<Object[]>();
            // ファイルごとにエラー情報を分けたいため、初期化
            this.errorInfo   = new HashMap<>();

            var isStarted  = false;
            var startTag   = XmlTagEnum.BIOPROJECT.start;
            var endTag     = XmlTagEnum.BIOPROJECT.end;
            var maximumRecord = this.config.search.maximumRecord;
            // メタデータの種別、ElasticsearchのIndex名にも使用する
            var type = TypeEnum.BIOPROJECT.type;
            // status, visibilityは固定値
            var status = StatusEnum.PUBLIC.status;
            var visibility = VisibilityEnum.UNRESTRICTED_ACCESS.visibility;
            var ddbjPrefix = "PRJD";

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入ってくる可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var json = this.jsonModule.xmlToJson(sb.toString());

                    var bean = this.getBean(json, path);

                    if(null == bean) {
                        continue;
                    }

                    var identifier = bean.getIdentifier();

                    // DDBJのアクセッションはスキップ
                    if(identifier.startsWith(ddbjPrefix)) {
                        continue;
                    }

                    requests.add(new IndexRequest(type).id(identifier).source(this.jsonModule.beanToJson(bean), XContentType.JSON));

                    if(requests.numberOfActions() == maximumRecord) {
                        this.searchModule.bulkInsert(requests);
                        requests = new BulkRequest();
                    }

                    recordList.add(new Object[] {
                            identifier,
                            status,
                            visibility,
                            null == bean.getDateCreated() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDateCreated()).getTime()),
                            null == bean.getDateModified() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDateModified()).getTime()),
                            null == bean.getDatePublished() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDatePublished()).getTime()),
                            json
                    });

                    if(recordList.size() == this.config.other.maximumRecord) {
                        this.bioProjectDao.bulkInsert(recordList);
                        recordList = new ArrayList<>();
                    }
                }
            }

            if(requests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(requests);
            }

            if(recordList.size() > 0) {
                this.bioProjectDao.bulkInsert(recordList);
            }

            for(Map.Entry<String, List<String>> entry : this.errorInfo.entrySet()) {
                // パース失敗したJsonの統計情報を出す
                var message = entry.getKey();
                var values  = entry.getValue();
                // パース失敗したサンプルのJsonを1つピックアップ
                var json    = values.get(0);
                var count   = values.size();

                log.error("Converting json to bean is failed:\t{}\t{}\t{}\t{}", message, count, path, json);
            }

            if(this.errorInfo.size() > 0) {
                this.messageModule.noticeErrorInfo(TypeEnum.BIOPROJECT.type, this.errorInfo);

            } else {
                var comment = String.format(
                        "%s\nbioproject validation success.",
                        this.config.message.mention
                );

                this.messageModule.postMessage(this.config.message.channelId, comment);
            }

        } catch (IOException | ParseException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        } finally {
            this.bioProjectDao.createIndex();
        }
    }

    public void registerDDBJ() {

        // DDBJ由来のレコードを削除
        this.ddbjBioProjectDao.dropIndex();
        this.ddbjBioProjectDao.deleteAll();

        if(this.searchModule.existsIndex("bioproject")) {
            var query = QueryBuilders.regexpQuery("identifier", "PRJD.*").rewrite("constant_score").caseInsensitive(true);
            var request = new DeleteByQueryRequest("bioproject").setQuery(query);
            this.searchModule.deleteByQuery(request);
        }

        var path = this.config.file.path.bioProject.ddbj;

        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            // Elasticsearch登録用
            var requests     = new BulkRequest();
            // Postgres登録用
            var recordList = new ArrayList<Object[]>();
            // ファイルごとにエラー情報を分けたいため、初期化
            this.errorInfo   = new HashMap<>();

            var isStarted  = false;
            var startTag   = XmlTagEnum.BIOPROJECT.start;
            var endTag     = XmlTagEnum.BIOPROJECT.end;
            var maximumRecord = this.config.search.maximumRecord;
            // メタデータの種別、ElasticsearchのIndex名にも使用する
            var type = TypeEnum.BIOPROJECT.type;
            // status, visibilityは固定値
            var status = StatusEnum.PUBLIC.status;
            var visibility = VisibilityEnum.UNRESTRICTED_ACCESS.visibility;

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入ってくる可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var json = this.jsonModule.xmlToJson(sb.toString());

                    var bean = this.getBean(json, path);

                    if(null == bean) {
                        continue;
                    }

                    var identifier = bean.getIdentifier();

                    requests.add(new IndexRequest(type).id(identifier).source(this.jsonModule.beanToJson(bean), XContentType.JSON));

                    if(requests.numberOfActions() == maximumRecord) {
                        this.searchModule.bulkInsert(requests);
                        requests = new BulkRequest();
                    }

                    recordList.add(new Object[] {
                            identifier,
                            status,
                            visibility,
                            null == bean.getDateCreated() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDateCreated()).getTime()),
                            null == bean.getDateModified() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDateModified()).getTime()),
                            null == bean.getDatePublished() ? null : new Timestamp(this.esSimpleDateFormat.parse(bean.getDatePublished()).getTime()),
                            json
                    });

                    if(recordList.size() == this.config.other.maximumRecord) {
                        this.ddbjBioProjectDao.bulkInsert(recordList);
                        recordList = new ArrayList<>();
                    }
                }
            }

            if(requests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(requests);
            }

            if(recordList.size() > 0) {
                this.ddbjBioProjectDao.bulkInsert(recordList);
            }

            for(Map.Entry<String, List<String>> entry : this.errorInfo.entrySet()) {
                // パース失敗したJsonの統計情報を出す
                var message = entry.getKey();
                var values  = entry.getValue();
                // パース失敗したサンプルのJsonを1つピックアップ
                var json    = values.get(0);
                var count   = values.size();

                log.error("Converting json to bean is failed:\t{}\t{}\t{}\t{}", message, count, path, json);
            }

            if(this.errorInfo.size() > 0) {
                this.messageModule.noticeErrorInfo(TypeEnum.BIOPROJECT.type, this.errorInfo);

            } else {
                var comment = String.format(
                        "%s\nbioproject validation success.",
                        this.config.message.mention
                );

                this.messageModule.postMessage(this.config.message.channelId, comment);
            }

        } catch (IOException | ParseException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        } finally {
            this.ddbjBioProjectDao.createIndex();
        }
    }

    public void validate(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb  = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.BIOPROJECT.start;
            var endTag    = XmlTagEnum.BIOPROJECT.end;

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
                    var json = this.jsonModule.xmlToJson(Objects.requireNonNull(sb).toString());
                    this.getProperties(json, path);
                }
            }

            if(this.errorInfo.size() > 0) {
                this.messageModule.noticeErrorInfo(TypeEnum.BIOPROJECT.type, this.errorInfo);

            } else {
                var comment = String.format(
                        "%s\nbioproject validation success.",
                        this.config.message.mention
                );

                this.messageModule.postMessage(this.config.message.channelId, comment);
            }

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void getMetadata() {
        var targetPath = "/bioproject/bioproject.xml";
        var targetDist = this.config.file.path.bioProject.ncbi;

        this.fileModule.delete(targetDist);
        log.info("Download {}.", targetPath);
        // ダウンロード
        this.fileModule.retrieveFile(this.config.file.ftp.ncbi, targetPath, targetDist);
        log.info("Complete download {}.", targetPath);
    }

    public void createUpdatedData(
            final String date,
            final String path,
            final CenterEnum center
    ) {
        if(null == date) {
            var message = "Date is null.";
            log.error(message);

            throw new DdbjException(message);
        }

        this.bioProjectDao.createTempTable(date);

        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            var sb = new StringBuilder();
            // Postgres登録用
            var recordList = new ArrayList<Object[]>();
            // ファイルごとにエラー情報を分けたいため、初期化
            this.errorInfo   = new HashMap<>();

            var isStarted  = false;
            var startTag   = XmlTagEnum.BIOPROJECT.start;
            var endTag     = XmlTagEnum.BIOPROJECT.end;
            var ddbjPrefix = "PRJD";
            // status, visibilityは固定値
            var status = StatusEnum.PUBLIC.status;
            var visibility = VisibilityEnum.UNRESTRICTED_ACCESS.visibility;

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

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    var properties = this.getProperties(json, path);

                    if(null == properties) {
                        continue;
                    }

                    var project = properties
                            .getProject()
                            .getProject();

                    var identifier = project
                            .getProjectID()
                            .getArchiveID()
                            .get(0)
                            .getAccession();

                    // 他局出力のファイルならDDBJのアクセッションはスキップ
                    if(center != CenterEnum.DDBJ
                    && identifier.startsWith(ddbjPrefix)) {
                        continue;
                    }

                    var projectDescr = project.getProjectDescr();

                    // FIXME DDBJ出力分のXMLにはSubmissionタグがないため、別の取得方法が必要
                    var submission = properties.getProject().getSubmission();

                    var datePublished = null == projectDescr.getProjectReleaseDate() ? null : this.jsonModule.parseOffsetDateTime(projectDescr.getProjectReleaseDate());
                    // 作成日時、更新日時がない場合は公開日時の値を代入する
                    // NCBIのサイトもそのような表示となっている https://www.ncbi.nlm.nih.gov/bioproject/16
                    var dateCreated   = null == submission || null == submission.getSubmitted() ? datePublished : this.jsonModule.parseLocalDate(submission.getSubmitted());
                    String dateModified;

                    if(null == submission || null == submission.getLastUpdate()) {
                        if(null != dateCreated) {
                            dateModified = dateCreated;
                        } else {
                            dateModified = datePublished;
                        }
                    } else {
                        dateModified = this.jsonModule.parseLocalDate(submission.getLastUpdate());
                    }

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
                        this.bioProjectDao.bulkInsertTemp(date, recordList);
                        recordList = new ArrayList<>();
                    }
                }
            }

            if(recordList.size() > 0) {
                this.bioProjectDao.bulkInsertTemp(date, recordList);
            }

            this.bioProjectDao.createTempIndex(date);

        } catch (IOException | ParseException e) {
            this.bioProjectDao.dropTempTable(date);

            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void update(final String date) {
        if(null == date) {
            var message = "Date is null.";
            log.error(message);

            throw new DdbjException(message);
        }

        var newRecords = this.bioProjectDao.selNewRecord(date);
        var unpublishedRecords = this.bioProjectDao.selToUnpublished(date);
        var updatedRecords = this.bioProjectDao.selUpdatedRecord(date);

        var requests = new BulkRequest();
        var maximumRecord = this.config.other.maximumRecord;
        // メタデータの種別、ElasticsearchのIndex名にも使用する
        var type = TypeEnum.BIOPROJECT.type;

        for (var record : newRecords) {
            var bean = this.getBean(record.getJson());

            if(null == bean) {
                continue;
            }

            var identifier = bean.getIdentifier();

            requests.add(new IndexRequest(type).id(identifier).source(this.jsonModule.beanToJson(bean), XContentType.JSON));

            if(requests.numberOfActions() == maximumRecord) {
                this.searchModule.bulkInsert(requests);
                requests = new BulkRequest();
            }
        }

        for (var record : unpublishedRecords) {
            var bean = this.getBean(record.getJson());

            if(null == bean) {
                continue;
            }

            var identifier = bean.getIdentifier();

            requests.add(new DeleteRequest(type).id(identifier));

            if(requests.numberOfActions() == maximumRecord) {
                this.searchModule.bulkInsert(requests);
                requests = new BulkRequest();
            }
        }

        for (var record : updatedRecords) {
            var bean = this.getBean(record.getJson());

            if(null == bean) {
                continue;
            }

            var identifier = bean.getIdentifier();

            requests.add(new UpdateRequest(type, identifier).doc( new IndexRequest(type).id(identifier).source(this.jsonModule.beanToJson(bean), XContentType.JSON)));

            if(requests.numberOfActions() == maximumRecord) {
                this.searchModule.bulkInsert(requests);
                requests = new BulkRequest();
            }
        }

        if(requests.numberOfActions() > 0) {
            this.searchModule.bulkInsert(requests);
        }

        // 更新が完了したら一時テーブルを本テーブルにする
        this.bioProjectDao.drop();
        this.bioProjectDao.rename(date);
        this.bioProjectDao.renameIndex(date);

        if(this.errorInfo.size() > 0) {
            this.messageModule.noticeErrorInfo(TypeEnum.BIOPROJECT.type, this.errorInfo);

        } else {
            var comment = String.format(
                    "%s\nUpdating BioProject's table is success.",
                    this.config.message.mention
            );

            this.messageModule.postMessage(this.config.message.channelId, comment);
        }
    }

    private Package getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = Converter.fromJsonString(json);

            return null == bean ? null : bean.getBioProjectPackage();
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
        // メタデータの種別、ElasticsearchのIndex名にも使用する
        var type = TypeEnum.BIOPROJECT.type;
        var isPartOf = IsPartOfEnum.BIOPROJECT.isPartOf;
        // status, visibilityは固定値
        var status = StatusEnum.PUBLIC.status;
        var visibility = VisibilityEnum.UNRESTRICTED_ACCESS.visibility;
        var geoUrl = "https://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=";
        var geoType = "GEO";
        var sraType = "SRA";

        // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
        var bioSampleType = TypeEnum.BIOSAMPLE.type;
        var submissionType = TypeEnum.SUBMISSION.type;
        var experimentType = TypeEnum.EXPERIMENT.type;
        var runType = TypeEnum.RUN.type;
        var studyType = TypeEnum.STUDY.type;
        var sampleType = TypeEnum.SAMPLE.type;

        // Json文字列を項目取得用、バリデーション用にBean化する
        // Beanにない項目がある場合はエラーを出力する
        var properties = this.getProperties(json, path);

        if(null == properties) {
            return null;
        }

        var project = properties
                .getProject()
                .getProject();

        var identifier = project
                .getProjectID()
                .getArchiveID()
                .get(0)
                .getAccession();

        var isDDBJ = identifier.startsWith("PRJDB");

        var projectDescr = project.getProjectDescr();

        var title = projectDescr.getTitle();

        var description = projectDescr.getDescription();

        var name = projectDescr.getName();

        var url = this.jsonModule.getUrl(type, identifier);
        List<SameAsBean> sameAs = null;
        var projectId = project.getProjectID();
        var centerIds = null == projectId ? null : projectId.getCenterID();

        if(!isDDBJ && null != centerIds) {
            // DDBJ出力分だとCenterIDが存在しない
            for (CenterID centerId : centerIds) {
                if (geoType.equals(centerId.getCenter())) {
                    sameAs = new ArrayList<>();
                    sameAs.add(new SameAsBean(
                            centerId.getContent(),
                            geoType,
                            geoUrl + centerId.getContent()
                    ));

                    break;
                }
            }
        }

        // FIXME NCBIだとdbGaPのIDも等価に扱われているため、sameAsに格納したほうが良い？　https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJNA215658
        // FIXME Unbrella Projectの扱い https://www.ncbi.nlm.nih.gov/bioproject/208232

        var projectTypeSubmission = project
                .getProjectType()
                .getProjectTypeSubmission();

        // 生物名とIDを設定
        var organismTarget =
                null == projectTypeSubmission
                        ? null
                        : projectTypeSubmission
                        .getTarget()
                        .getOrganism();

        var organismName =
                null == organismTarget
                        ? null
                        : organismTarget.getOrganismName();

        var organismIdentifier =
                null == organismTarget
                        ? null
                        : organismTarget.getTaxID();

        var organism = this.jsonModule.getOrganism(organismName, organismIdentifier);

        var dbXrefs = new ArrayList<DBXrefsBean>();

        // sra-studyを取得
        var externalLink = projectDescr.getExternalLink();
        var studyDbXrefs = new ArrayList<DBXrefsBean>();

        if(null != externalLink) {
            for (var ex : externalLink) {
                var dbXREF = ex.getDBXREF();

                if(null != dbXREF
                        && sraType.equals(dbXREF.getDB())
                        && null != dbXREF.getID()) {
                    var studyId = dbXREF.getID();
                    var studyUrl = this.jsonModule.getUrl(studyType, studyId);

                    var bean = new DBXrefsBean(
                            studyId,
                            studyType,
                            studyUrl
                    );

                    studyDbXrefs.add(bean);

                    sameAs = null == sameAs ? new ArrayList<>() : sameAs;
                    var item = new SameAsBean(
                            studyId,
                            studyType,
                            studyUrl
                    );
                    sameAs.add(item);
                }
            }
        }

        // 重複チェック用
        var duplicatedCheck = new HashSet<String>();

        // biosample、sample取得
        var bioSampleDbXrefs = new ArrayList<DBXrefsBean>();
        var bioSampleIdList = new ArrayList<String>();
        var locusTagPrefix = projectDescr.getLocusTagPrefix();

        if(null != locusTagPrefix) {
            for(var locus : locusTagPrefix) {
                var biosampleId = locus.getBiosampleID();

                if(null == biosampleId || duplicatedCheck.contains(biosampleId)) {
                    continue;
                }

                bioSampleDbXrefs.add(new DBXrefsBean(biosampleId, bioSampleType, this.jsonModule.getUrl(bioSampleType, biosampleId)));
                bioSampleIdList.add(biosampleId);
                duplicatedCheck.add(biosampleId);
            }
        }

        // FIXME BioSample、Sampleが足りない
        //  - https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJNA208369
        //  - https://ddbj-staging.nig.ac.jp/resource/bioproject/PRJNA208369
        //  - BioSampleはこのあたりから取ればよいのかも（でもBioProjectは…？
//                    "Links" : {
//                        "Link" : [
//                        {
//                            "label" : "GEO Sample GSM1529050",
//                                "type" : "url",
//                                "content" : "http://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=GSM1529050"
//                        },
//                        {
//                                "target" : "bioproject",
//                                "label" : "PRJNA208369",
//                                "type" : "entrez",
//                                "content" : "208369"
//                        },
//                        {
//                                "target" : "bioproject",
//                                "label" : "PRJNA264621",
//                                "type" : "entrez",
//                                "content" : "264621"
//                        }
//        ]
//                    },

        // submission、runを取得、biosample, sampleもLocusTagPrefixからは取得できなかったものもあるため、再度取得
        // 取得できなかったデータ　https://ddbj-staging.nig.ac.jp/resource/bioproject/PRJNA229482
        List<AccessionsBean> runList;

        if(isDDBJ) {
            runList = this.draAccessionDao.selRunByBioProject(identifier);
        } else {
            runList = this.runDao.selByBioProject(identifier);
        }

        var submissionDbXrefs = new ArrayList<DBXrefsBean>();
        var experimentDbXrefs = new ArrayList<DBXrefsBean>();
        var runDbXrefs = new ArrayList<DBXrefsBean>();
        List<DBXrefsBean> sampleDbXrefs = new ArrayList<>();

        for(var run: runList) {
            var bioSampleId = run.getBioSample();
            var submissionId = run.getSubmission();
            var experimentId = run.getExperiment();
            var runId = run.getAccession();
            var sampleId = run.getSample();

            if(!duplicatedCheck.contains(bioSampleId)) {
                bioSampleDbXrefs.add(this.jsonModule.getDBXrefs(bioSampleId, bioSampleType));
                duplicatedCheck.add(bioSampleId);
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

            if(!duplicatedCheck.contains(sampleId)) {
                sampleDbXrefs.add(this.jsonModule.getDBXrefs(sampleId, sampleType));
                duplicatedCheck.add(sampleId);
            }

        }

        if(bioSampleIdList.size() > 0) {
            if(isDDBJ) {
                bioSampleDbXrefs.addAll(this.draAccessionDao.selByBioSampleList(bioSampleIdList));
            } else {
                bioSampleDbXrefs.addAll(this.sampleDao.selByBioSampleList(bioSampleIdList));
            }
        }

        // analysisを取得
        List<DBXrefsBean> analysisDbXrefs;

        if(isDDBJ) {
            analysisDbXrefs = this.draAccessionDao.selAnalysisByBioProject(identifier);
        } else {
            analysisDbXrefs = this.analysisDao.selByBioProject(identifier);
        }

        // biosample→submission→experiment→run→analysis→study→sampleの順でDbXrefsを格納していく
        dbXrefs.addAll(bioSampleDbXrefs);
        dbXrefs.addAll(submissionDbXrefs);
        dbXrefs.addAll(experimentDbXrefs);
        dbXrefs.addAll(runDbXrefs);
        dbXrefs.addAll(analysisDbXrefs);
        dbXrefs.addAll(studyDbXrefs);
        dbXrefs.addAll(sampleDbXrefs);

        var distribution = this.jsonModule.getDistribution(TypeEnum.BIOPROJECT.type, identifier);
        List<DownloadUrlBean> downloadUrl = null;
        String dateCreated;
        String dateModified;
        String datePublished;

        if (isDDBJ) {
            var date = this.exBioProjectDao.select(identifier);

            dateCreated = this.jsonModule.parseLocalDateTime(null == date ? null : date.getDateCreated());
            dateModified = this.jsonModule.parseLocalDateTime(null == date ? null : date.getDateModified());
            datePublished = this.jsonModule.parseLocalDateTime(null == date ? null : date.getDatePublished());

        } else {
            var submission = properties.getProject().getSubmission();

            datePublished = this.jsonModule.parseOffsetDateTime(projectDescr.getProjectReleaseDate());
            // 作成日時、更新日時がない場合は公開日時の値を代入する
            // NCBIのサイトもそのような表示となっている https://www.ncbi.nlm.nih.gov/bioproject/16
            dateCreated   = null == submission || null == submission.getSubmitted() ? datePublished : this.jsonModule.parseLocalDate(submission.getSubmitted());

            if(null == submission || null == submission.getLastUpdate()) {
                if(null != dateCreated) {
                    dateModified = dateCreated;
                } else {
                    dateModified = datePublished;
                }
            } else {
                dateModified = this.jsonModule.parseLocalDate(submission.getLastUpdate());
            }
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
                properties,
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
