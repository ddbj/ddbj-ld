package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.common.SuppressedMetadataDao;
import com.ddbj.ld.app.transact.dao.sra.SraRunDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.sra.run.RUNClass;
import com.ddbj.ld.data.beans.sra.run.RunConverter;
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
public class SraRunService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final MessageModule messageModule;
    private final SearchModule searchModule;

    private final SraRunDao runDao;
    private final SuppressedMetadataDao suppressedMetadataDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public List<UpdateRequest> get(final String path) {
        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb = null;
            var requests = new ArrayList<UpdateRequest>();

            var isStarted = false;

            // 固定値
            var startTag  = XmlTagEnum.SRA_RUN.start;
            var endTag    = XmlTagEnum.SRA_RUN.end;
            var type = TypeEnum.RUN.getType();

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
        var type = TypeEnum.RUN.type;

        // suppressedからpublic, unpublishedになったデータはsuppressedテーブルから削除する
        var suppressedToPublicRecords = this.runDao.selSuppressedToPublic(date);
        var suppressedToUnpublishedRecords = this.runDao.selSuppressedToUnpublished(date);
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

        var toSuppressedRecords = this.runDao.selToSuppressed(date);
        var toUnpublishedRecords = this.runDao.selToUnpublished(date);

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
            var startTag  = XmlTagEnum.SRA_RUN.start;
            var endTag    = XmlTagEnum.SRA_RUN.end;

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
            this.messageModule.noticeErrorInfo(TypeEnum.RUN.type, this.errorInfo);

        } else {
            var comment = String.format(
                    "%s\nsra-run validation success.",
                    this.config.message.mention
            );

            this.messageModule.postMessage(this.config.message.channelId, comment);
        }

        this.errorInfo = new HashMap<>();
    }

    public void rename(final String date) {
        this.runDao.drop();
        this.runDao.rename(date);
        this.runDao.renameIndex(date);
    }

    private RUNClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = RunConverter.fromJsonString(json);

            return bean.getRun();
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
        // 固定値
        // typeの設定
        var type = TypeEnum.RUN.getType();
        // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
        List<SameAsBean> sameAs = null;
        // "DRA"固定
        var isPartOf = IsPartOfEnum.SRA.getIsPartOf();
        // 生物名とIDはSampleのみの情報であるため空情報を設定
        OrganismBean organism = null;

        // 処理で使用する関連オブジェクトの種別、dbXrefs、sameAsなどで使用する
        var bioProjectType = TypeEnum.BIOPROJECT.type;
        var bioSampleType = TypeEnum.BIOSAMPLE.type;
        var submissionType = TypeEnum.SUBMISSION.type;
        var experimentType = TypeEnum.EXPERIMENT.type;
        var studyType = TypeEnum.STUDY.type;
        var sampleType = TypeEnum.SAMPLE.type;

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

        // Description に該当するデータは存在しないためrunではnullを設定
        String description = null;

        // name 取得
        var name = properties.getAlias();

        // sra-run/[DES]RA??????
        var url = this.jsonModule.getUrl(type, identifier);

        var distribution = this.jsonModule.getDistribution(type, identifier);

        var dbXrefs = new ArrayList<DBXrefsBean>();

        // runはanalysis以外一括で取得できる
        // bioproject、biosample、submission、experiment、study、sample、status、visibility、date_created、date_modified、date_published
        var run = this.runDao.select(identifier);

        if(null != run) {
            dbXrefs.add(this.jsonModule.getDBXrefs(run.getBioProject(), bioProjectType));
            dbXrefs.add(this.jsonModule.getDBXrefs(run.getBioSample(), bioSampleType));
            dbXrefs.add(this.jsonModule.getDBXrefs(run.getSubmission(), submissionType));
            dbXrefs.add(this.jsonModule.getDBXrefs(run.getExperiment(), experimentType));
            dbXrefs.add(this.jsonModule.getDBXrefs(run.getStudy(), studyType));
            dbXrefs.add(this.jsonModule.getDBXrefs(run.getSample(), sampleType));
        }

        // status, visibility、日付取得処理
        var status = null == run ? StatusEnum.PUBLIC.status : run.getStatus();
        var visibility = null == run ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : run.getVisibility();
        var dateCreated = null == run ? null : this.jsonModule.parseLocalDateTime(run.getReceived());
        var dateModified = null == run ? null : this.jsonModule.parseLocalDateTime(run.getUpdated());
        var datePublished = null == run ? null : this.jsonModule.parseLocalDateTime(run.getPublished());

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
                status,
                visibility,
                dateCreated,
                dateModified,
                datePublished
        );
    }
}
