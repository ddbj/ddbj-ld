package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.common.SuppressedMetadataDao;
import com.ddbj.ld.app.transact.dao.sra.DraLiveListDao;
import com.ddbj.ld.app.transact.dao.sra.SraAnalysisDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.analysis.ANALYSISClass;
import com.ddbj.ld.data.beans.sra.analysis.AnalysisConverter;
import com.ddbj.ld.data.beans.sra.common.ID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SraAnalysisService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final MessageModule messageModule;
    private final SearchModule searchModule;

    private final SraAnalysisDao analysisDao;
    private final SuppressedMetadataDao suppressedMetadataDao;
    private final DraLiveListDao draLiveListDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<UpdateRequest> get(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var requests = new ArrayList<UpdateRequest>();

            var isStarted = false;

            // 固定値
            var startTag  = XmlTagEnum.SRA_ANALYSIS.start;
            var endTag    = XmlTagEnum.SRA_ANALYSIS.end;
            var type = TypeEnum.ANALYSIS.getType();

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
                    var json = this.jsonModule.xmlToJson(sb.toString());
                    var bean = this.getBean(json, path);

                    if(null == bean) {
                        continue;
                    }

                    var identifier = bean.getIdentifier();
                    var doc = this.jsonModule.beanToJson(bean);
                    var indexRequest = new IndexRequest(type).id(identifier).source(doc, XContentType.JSON);
                    var updateRequest = new UpdateRequest(type, identifier).upsert(indexRequest).doc(doc, XContentType.JSON);

                    requests.add(updateRequest);
                }
            }

            return requests;

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public List<DeleteRequest> getDeleteRequests(final String date) {
        var type = TypeEnum.ANALYSIS.type;

        // suppressedからpublic, unpublishedになったデータはsuppressedテーブルから削除する
        var suppressedToPublicRecords = this.analysisDao.selSuppressedToPublic(date);
        var suppressedToUnpublishedRecords = this.analysisDao.selSuppressedToUnpublished(date);
        var suppressedArgs = new ArrayList<Object[]>();

        for (var record : suppressedToPublicRecords) {
            suppressedArgs.add(new Object[] {
                    record.getAccession()
            });
        }

        for (var record : suppressedToUnpublishedRecords) {
            suppressedArgs.add(new Object[] {
                    record.getAccession()
            });
        }

        this.suppressedMetadataDao.bulkDelete(suppressedArgs);

        var toSuppressedRecords = this.analysisDao.selToSuppressed(date);
        var toUnpublishedRecords = this.analysisDao.selToUnpublished(date);

        var deleteRequests = new ArrayList<DeleteRequest>();
        var getRequests = new MultiGetRequest();

        for(var record : toSuppressedRecords) {
            var identifier = record.getAccession();
            deleteRequests.add(new DeleteRequest(type).id(identifier));

            getRequests.add(new MultiGetRequest.Item(type, identifier));
        }

        for(var record : toUnpublishedRecords) {
            var identifier = record.getAccession();
            deleteRequests.add(new DeleteRequest(type).id(identifier));
        }

        // suppressedとなったレコードをSuppressedテーブルに登録
        if(getRequests.getItems().size() > 0) {
            var getResponses = this.searchModule.get(getRequests);
            var recordList = new ArrayList<Object[]>();

            for(var response: getResponses) {
                var res = response.getResponse();

                if(null == res) {
                    continue;
                }

                var source = res.getSourceAsString();
                var json = this.jsonModule.paintPretty(source);

                if(null == source) {
                    log.error("Getting data from elasticsearch is failed: {}", response.getId());

                    continue;
                }

                recordList.add(new Object[] {
                        response.getId(),
                        type,
                        json
                });
            }

            this.suppressedMetadataDao.bulkInsert(recordList);
        }

        return deleteRequests;
    }

    public void printErrorInfo() {
        this.jsonModule.printErrorInfo(this.errorInfo);
    }

    public void validate(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_ANALYSIS.start;
            var endTag    = XmlTagEnum.SRA_ANALYSIS.end;

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
                    var json = this.jsonModule.xmlToJson(sb.toString());
                    this.getProperties(json, path);
                }
            }

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void noticeErrorInfo() {
        if(this.errorInfo.size() > 0) {
            this.messageModule.noticeErrorInfo(TypeEnum.ANALYSIS.type, this.errorInfo);

        } else {
            var comment = String.format(
                    "%s\nsra-analysis validation success.",
                    this.config.message.mention
            );

            this.messageModule.postMessage(this.config.message.channelId, comment);
        }

        this.errorInfo = new HashMap<>();
    }

    public void rename(final String date) {
        this.analysisDao.drop();
        this.analysisDao.rename(date);
        this.analysisDao.renameIndex(date);
    }

    public ArrayList<AccessionsBean> getDraAccessionList(
            final String path,
            final String submissionId
    ) {
        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.SRA_ANALYSIS.start;
            var endTag    = XmlTagEnum.SRA_ANALYSIS.end;

            var accessionList = new ArrayList<AccessionsBean>();

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
                    var json = this.jsonModule.xmlToJson(sb.toString());
                    var properties = this.getProperties(json, path);

                    if(null == properties) {
                        continue;
                    }

                    var accession = properties.getAccession();
                    var studyRef = properties.getStudyRef();
                    var bioProjectId = null == studyRef ? null : studyRef.getAccession();

                    var liveList = this.draLiveListDao.select(accession, submissionId);

                    var bean = new AccessionsBean(
                            accession,
                            liveList.getSubmission(),
                            StatusEnum.PUBLIC.status,
                            liveList.getUpdated(),
                            liveList.getUpdated(),
                            null,
                            liveList.getType(),
                            liveList.getCenter(),
                            "public".equals(liveList.getVisibility()) ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : VisibilityEnum.CONTROLLED_ACCESS.visibility,
                            liveList.getAlias(),
                            null,
                            null,
                            null,
                            (byte) 1,
                            null,
                            null,
                            liveList.getMd5sum(),
                            null,
                            bioProjectId,
                            null,
                            null,
                            null
                    );

                    accessionList.add(bean);
                }
            }

            return accessionList;

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    private ANALYSISClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = AnalysisConverter.fromJsonString(json);

            return bean.getAnalysis();
        } catch (IOException e) {
            var message = e.getLocalizedMessage()
                    .replaceAll("\n at.*.", "")
                    .replaceAll("\\(.*.", "");
            log.error("Converting metadata to bean is failed. xml path: {}, json:{}, message: {}", path, json, message, e);

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
        // 固定値
        // typeの設定
        var type = TypeEnum.ANALYSIS.getType();
        // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
        List<SameAsBean> sameAs = null;
        // "SRA"固定
        var isPartOf = IsPartOfEnum.SRA.getIsPartOf();
        // 生物名とIDはSampleのみの情報であるため空情報を設定
        OrganismBean organism = null;

        // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
        var bioProjectType = TypeEnum.BIOPROJECT.type;
        var submissionType = TypeEnum.SUBMISSION.type;
        var studyType = TypeEnum.STUDY.type;

        // Json文字列を項目取得用、バリデーション用にBean化する
        // Beanにない項目がある場合はエラーを出力する
        var properties = this.getProperties(json, path);

        if(null == properties) {
            return null;
        }

        // accesion取得
        var identifier = properties.getAccession();

        // Title取得
        var title = properties.getTitle();

        // Description 取得
        var description = properties.getDescription();

        // name 取得
        var name = properties.getAlias();

        // sra-analysis/[DES]RA??????
        var url = this.jsonModule.getUrl(type, identifier);

        var distribution = this.jsonModule.getDistribution(type, identifier);

        var dbXrefs = new ArrayList<DBXrefsBean>();

        // bioproject、submission、study、status、visibility、date_created、date_modified、date_published
        var analysis = this.analysisDao.select(identifier);
        var bioProjectId = null == analysis ? null : analysis.getBioProject();
        var submissionId = null == analysis ? null : analysis.getSubmission();
        var studyId = null == analysis ? null : analysis.getStudy();

        if(null != bioProjectId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(bioProjectId, bioProjectType));
        }

        if(null != submissionId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(submissionId, submissionType));
        }

        if(null != studyId) {
            dbXrefs.add(this.jsonModule.getDBXrefs(studyId, studyType));
        }

        List<DownloadUrlBean> downloadUrl = null;
        var dataBlocks = properties.getDataBlock();

        if(null != dataBlocks) {
            for(var dataBlock : dataBlocks) {
                var files = null == dataBlock.getFiles() ? null : dataBlock.getFiles().getFile();
                downloadUrl = null == downloadUrl ? new ArrayList<>() : downloadUrl;

                // NCBI由来のSRAだったら後続処理をつけずスキップし固定値を入れる
                if(identifier.startsWith("SRZ")) {
                    downloadUrl.add(new DownloadUrlBean(
                            null,
                            null,
                            "https://trace.ncbi.nlm.nih.gov/Traces/sra/sra.cgi?analysis=" + identifier,
                            null
                    ));

                    break;
                }

                // 根本のURLを作る
                var httpsRoot = "";
                var ftpRoot = "";

                if(identifier.startsWith("DRZ")) {
                    var submissionPrefix = null == submissionId ? null : submissionId.substring(0, 6);
                    httpsRoot = "https://ddbj.nig.ac.jp/public/ddbj_database/dra/fastq/" + submissionPrefix + "/" + submissionId + "/" + identifier + "/provisional/";
                    ftpRoot = "ftp://ftp.ddbj.nig.ac.jp/ddbj_database/dra/fastq/" + submissionPrefix + "/" + submissionId + "/" + identifier + "/provisional/";
                } else if(identifier.startsWith("ERZ")) {
                    var prefix = identifier.substring(0, 6);
                    httpsRoot = "ftp://ftp.sra.ebi.ac.uk/vol1/" + prefix + "/" + identifier + "/";
                    ftpRoot = "https://ftp.sra.ebi.ac.uk/vol1/" + prefix + "/" + identifier + "/";
                } else {
                    log.error("indentifier is invalid: {}", identifier);

                    break;
                }

                for(var file : files) {
                    var fileName = file.getFilename();

                    downloadUrl.add(new DownloadUrlBean(
                            file.getFiletype(),
                            fileName,
                            httpsRoot,
                            ftpRoot
                    ));
                }
            }
        }

        // status, visibility、日付取得処理
        var status = null == analysis ? StatusEnum.PUBLIC.status : analysis.getStatus();
        var visibility = null == analysis ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : analysis.getVisibility();
        var dateCreated = null == analysis ? null : this.jsonModule.parseLocalDateTime(analysis.getReceived());
        var dateModified = null == analysis ? null : this.jsonModule.parseLocalDateTime(analysis.getUpdated());
        var datePublished = null == analysis ? null : this.jsonModule.parseLocalDateTime(analysis.getPublished());

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
}
