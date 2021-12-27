package com.ddbj.ld.app.transact.service.jga;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.primary.jga.*;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.DownloadUrlBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.jga.dac.DACClass;
import com.ddbj.ld.data.beans.jga.dac.DacConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class JGADacService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;
    private final MessageModule messageModule;

    private final JGADateDao dateDao;
    private final JGAExperimentStudyDao experimentStudyDao;
    private final JGAPolicyDacDao policyDacDao;
    private final JGADataSetDataDao dataSetDataDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public void register() {

        var path = this.config.file.path.jga.dac;

        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb  = null;
            var requests     = new BulkRequest();
            var maximumRecord = this.config.search.maximumRecord;

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_DAC.start;
            var endTag    = XmlTagEnum.JGA_DAC.end;

            var type               = TypeEnum.JGA_DAC.type;
            var isPartOf           = IsPartOfEnum.JGA.isPartOf;
            var organismName       = OrganismEnum.HOMO_SAPIENS.name;
            var organismIdentifier = OrganismEnum.HOMO_SAPIENS.identifier;
            var organism           = this.jsonModule.getOrganism(organismName, organismIdentifier);
            var status = StatusEnum.PUBLIC.status;
            var visibility = VisibilityEnum.UNRESTRICTED_ACCESS.visibility;

            if(this.searchModule.existsIndex(type)) {
                this.searchModule.deleteIndex(type);
            }

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
                    var properties = this.getProperties(json, path);

                    if(null == properties) {
                        continue;
                    }

                    // 共通項目以外のJson項目を作る
                    var identifier = properties.getAccession();
                    var title = properties.getTitle();
                    String description = null;
                    var name = properties.getAlias();
                    var url = this.jsonModule.getUrl(type, identifier);
                    // FIXME SameAsのマッピング(SECONDARY_IDか？
                    List<SameAsBean> sameAs = null;
                    var distribution = this.jsonModule.getDistribution(type, identifier);
                    List<DownloadUrlBean> downloadUrl = null;

                    // DbXrefsのデータを作成
                    // オブジェクト間の関係性を取得する Study (1) → Dataset (n) → Policy (1) → DAC (NBDC, 1)
                    // t_jga_experiment_studyとt_jga_analysis_studyをUNIONし全てのStudyを取得
                    // t_jga_dataset_dataとt_jga_dataset_analysisをUNIONし全てのDataSetを取得
                    // t_jga_policy_dacから全てのPolicyを取得
                    // PolicyからDacを取得(Dacは1つで固定のためDBからの取得は不要
                    var dbXrefs = new ArrayList<DBXrefsBean>();
                    var studyList  = this.experimentStudyDao.selAllStudy();
                    var datasetList = this.dataSetDataDao.selAllDataSet();
                    var policyList = this.policyDacDao.selAllPolicy();
                    dbXrefs.addAll(studyList);
                    dbXrefs.addAll(datasetList);
                    dbXrefs.addAll(policyList);

                    // 日付のデータを作成
                    var dateInfo = this.dateDao.selJgaDate(identifier);
                    var datePublished = this.jsonModule.parseTimestamp((Timestamp)dateInfo.get("date_published"));
                    var dateCreated   = this.jsonModule.parseTimestamp((Timestamp)dateInfo.get("date_created"));
                    var dateModified  = this.jsonModule.parseTimestamp((Timestamp)dateInfo.get("date_modified"));

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
                            downloadUrl,
                            status,
                            visibility,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

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
            }

            if(requests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(requests);
            }

            if(this.errorInfo.size() > 0) {
                this.messageModule.noticeErrorInfo(type, this.errorInfo);
            }

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void validate() {
        var path = this.config.file.path.jga.dac;

        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb  = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_DAC.start;
            var endTag    = XmlTagEnum.JGA_DAC.end;

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
                this.messageModule.noticeErrorInfo(TypeEnum.JGA_DAC.type, this.errorInfo);

            } else {
                var comment = String.format(
                        "%s\njga-dac validation success.",
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

    private DACClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = DacConverter.fromJsonString(json);

            return bean.getDAC();
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
