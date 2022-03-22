package com.ddbj.ld.app.transact.service.jga;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.primary.jga.JGADataSetDataDao;
import com.ddbj.ld.app.transact.dao.primary.jga.JGADataSetPolicyDao;
import com.ddbj.ld.app.transact.dao.primary.jga.JGADateDao;
import com.ddbj.ld.common.constants.*;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.jga.dataset.DATASETClass;
import com.ddbj.ld.data.beans.jga.dataset.DatasetConverter;
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
public class JGADataSetService {

    private final ConfigSet config;

    private final JsonModule jsonModule;
    private final SearchModule searchModule;
    private final MessageModule messageModule;

    private final JGADateDao dateDao;
    private final JGADataSetPolicyDao dataSetPolicyDao;
    private final JGADataSetDataDao dataSetDataDao;

    // XMLをパース失敗した際に出力されるエラーを格納
    private HashMap<String, List<String>> errorInfo;

    public void register() {

        var path = this.config.file.path.jga.dataSet;

        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb  = null;
            var requests     = new BulkRequest();
            var maximumRecord = this.config.search.maximumRecord;

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_DATASET.start;
            var endTag    = XmlTagEnum.JGA_DATASET.end;

            // JSONに使用する項目のうち固定値のもの
            var dacIdentifier = "JGAC000001";
            var dacType       = TypeEnum.JGA_DAC.type;
            var dac = new DBXrefsBean(
                    dacIdentifier,
                    dacType,
                    this.jsonModule.getUrl(TypeEnum.JGA_DAC.type, dacIdentifier)
            );
            var type               = TypeEnum.JGA_DATASET.type;
            var isPartOf           = IsPartOfEnum.JGA.isPartOf;
            var organismName       = OrganismEnum.HOMO_SAPIENS.name;
            var organismIdentifier = OrganismEnum.HOMO_SAPIENS.identifier;
            var organism           = this.jsonModule.getOrganism(organismName, organismIdentifier);
            var status = StatusEnum.PUBLIC.status;
            var visibility = VisibilityEnum.UNRESTRICTED_ACCESS.visibility;
            // DACは1固定のため
            var dacStatistics = new DBXrefsStatisticsBean(TypeEnum.JGA_DAC.type, 1);

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
                    var description = properties.getDescription();
                    var name = properties.getAlias();
                    var url = this.jsonModule.getUrl(type, identifier);
                    // FIXME SameAsのマッピング(SECONDARY_IDか？
                    List<SameAsBean> sameAs = null;
                    var search = this.jsonModule.beanToJson(properties);
                    var distribution = this.jsonModule.getDistribution(type, identifier);
                    List<DownloadUrlBean> downloadUrl = null;

                    // DbXrefsのデータを作成
                    // オブジェクト間の関係性を取得する Study (1) → Dataset (n) → Policy (1) → DAC (NBDC, 1)
                    // DatasetからPolicyを取得
                    // Dataset→Data→Experimentと経由してStudyを取得
                    // Dacは1つで固定のためDBからの取得は不要
                    var dbXrefs = new ArrayList<DBXrefsBean>();
                    var studyList  = this.dataSetDataDao.selStudy(identifier);
                    var policyList = this.dataSetPolicyDao.selPolicy(identifier);
                    dbXrefs.addAll(policyList);
                    dbXrefs.addAll(studyList);
                    dbXrefs.add(dac);

                    var dbXrefsStatistics = new ArrayList<DBXrefsStatisticsBean>();

                    dbXrefsStatistics.add(new DBXrefsStatisticsBean(TypeEnum.JGA_STUDY.type, studyList.size()));
                    dbXrefsStatistics.add(new DBXrefsStatisticsBean(TypeEnum.JGA_POLICY.type, policyList.size()));
                    dbXrefsStatistics.add(dacStatistics);

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
        var path = this.config.file.path.jga.dataSet;

        try (var br = new BufferedReader(new FileReader(path))) {

            String line;
            StringBuilder sb  = null;

            var isStarted = false;
            var startTag  = XmlTagEnum.JGA_DATASET.start;
            var endTag    = XmlTagEnum.JGA_DATASET.end;

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
                this.messageModule.noticeErrorInfo(TypeEnum.JGA_DATASET.type, this.errorInfo);
            }

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    private DATASETClass getProperties(
            final String json,
            final String path
    ) {
        try {
            var bean = DatasetConverter.fromJsonString(json);

            return bean.getDataset();
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
