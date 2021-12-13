package com.ddbj.ld.app.core.module;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.common.annotation.Module;
import com.ddbj.ld.common.constants.DistributionEnum;
import com.ddbj.ld.common.exception.DdbjException;
import com.ddbj.ld.data.beans.common.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.XML;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Elasticsearchに登録するJsonの項目を作成する処理でトランザクションを伴わないものを格納するクラス.
 */
@Module
@AllArgsConstructor
@Slf4j
public class JsonModule {

    private final ConfigSet config;
    private final SimpleDateFormat esSimpleDateFormat;
    private final DateTimeFormatter esFormatter;
    private final ObjectMapper objectMapper;

    public String getUrl(
            final String type,
            final String identifier
    ) {
        return this.config.search.resourceUrl + type + "/" + identifier;
    }

    public String getUrl(
            final String type,
            final String identifier,
            final String extension
    ) {
        return this.config.search.resourceUrl + type + "/" + identifier + extension;
    }

    public OrganismBean getOrganism(String name, String identifier) {
        OrganismBean organismBean = new OrganismBean();
        organismBean.setName(name);
        organismBean.setIdentifier(identifier);

        return organismBean;
    }

    public List<DistributionBean> getDistribution(
            final String type,
            final String identifier
    ) {
        var distributionBeanList = new ArrayList<DistributionBean>();
        var jsonDistributionBean = new DistributionBean();
        var jsonLdDistributionBean = new DistributionBean();

        var jsonType   = DistributionEnum.TYPE_DATA_DOWNLOAD.getItem();
        var jsonLdType = DistributionEnum.TYPE_DATA_DOWNLOAD.getItem();

        var jsonEncodingFormat   = DistributionEnum.ENCODING_FORMAT_JSON.getItem();
        var jsonLdEncodingFormat = DistributionEnum.ENCODING_FORMAT_JSON_LD.getItem();

        var jsonExtension      = DistributionEnum.CONTACT_URL_EXTENTION_JSON.getItem();
        String jsonLdExtension = DistributionEnum.CONTACT_URL_EXTENTION_JSON_LD.getItem();

        var jsonContentUrl      = this.getUrl(type, identifier, jsonExtension);
        var jsonConLdContentUrl = this.getUrl(type, identifier, jsonLdExtension);

        jsonDistributionBean.setType(jsonType);
        jsonLdDistributionBean.setType(jsonLdType);

        jsonDistributionBean.setEncodingFormat(jsonEncodingFormat);
        jsonLdDistributionBean.setEncodingFormat(jsonLdEncodingFormat);

        jsonDistributionBean.setContentUrl(jsonContentUrl);
        jsonLdDistributionBean.setContentUrl(jsonConLdContentUrl);

        distributionBeanList.add(jsonDistributionBean);
        distributionBeanList.add(jsonLdDistributionBean);

        return distributionBeanList;
    }

    public String parseTimestamp(Timestamp timestamp) {
        try {
            return null == timestamp ? null : this.esSimpleDateFormat.format(timestamp);
        } catch(Exception e) {
            log.error("Converting Timestamp to String is failed.", e);
            return null;
        }
    }

    public String parseOffsetDateTime(
            final OffsetDateTime timestamp
    ) {
        try {
            return null == timestamp ? null : timestamp.format(this.esFormatter);
        } catch(Exception e) {
            log.error("Converting OffsetDateTime to String is failed.", e);
            return null;
        }
    }

    public String parseLocalDate(
            final LocalDate localDate
    ) {
        try {
            return null == localDate ? null : this.esSimpleDateFormat.format(Timestamp.valueOf(localDate.atStartOfDay()));
        } catch(Exception e) {
            log.error("Converting LocalDate to String is failed.", e);
            return null;
        }
    }

    public String parseLocalDateTime(
            final LocalDateTime localDateTime
    ) {
        try {
            return null == localDateTime ? null : this.esSimpleDateFormat.format(Timestamp.valueOf(localDateTime));
        } catch(Exception e) {
            log.error("Converting LocalDateTime to String is failed.", e);
            return null;
        }
    }

    public void printErrorInfo(final HashMap<String, List<String>> errorInfo) {
        for (Map.Entry<String, List<String>> entry : errorInfo.entrySet()) {
            // パース失敗したJsonの統計情報を出す
            var message = entry.getKey();
            var values  = entry.getValue();
            // パース失敗したサンプルのJsonを1つピックアップ
            var json    = values.get(0);
            var count   = values.size();

            log.error("Converting json to bean is failed:\t{}\t{}\t{}", message, count, json);
        }
    }

    public AccessionsBean getAccessions(final ResultSet rs) {
        try {
            var accession  = rs.getString("accession");
            var submission = rs.getString("submission");
            var status = rs.getString("status");
            var updated = null == rs.getTimestamp("updated") ? null : rs.getTimestamp("updated").toLocalDateTime();
            var published = null == rs.getTimestamp("published") ? null : rs.getTimestamp("published").toLocalDateTime();
            var received = null == rs.getTimestamp("received") ? null : rs.getTimestamp("received").toLocalDateTime();
            var type = rs.getString("type");
            var center = rs.getString("center");
            var visibility = rs.getString("visibility");
            var alias = rs.getString("alias");
            var experiment = rs.getString("experiment");
            var sample = rs.getString("sample");
            var study = rs.getString("study");
            var loaded = rs.getByte("loaded");
            var spots = rs.getString("spots");
            var bases = rs.getString("bases");
            var md5sum = rs.getString("md5sum");
            var bioSample = rs.getString("biosample");
            var bioProject = rs.getString("bioproject");
            var replacedBy = rs.getString("replacedby");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            var updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

            return new AccessionsBean(
                    accession,
                    submission,
                    status,
                    updated,
                    published,
                    received,
                    type,
                    center,
                    visibility,
                    alias,
                    experiment,
                    sample,
                    study,
                    loaded,
                    spots,
                    bases,
                    md5sum,
                    bioSample,
                    bioProject,
                    replacedBy,
                    createdAt,
                    updatedAt
            );
        } catch (SQLException e) {
            log.error("Converting accessions is failed.", e);

            return null;
        }
    }

    public DBXrefsBean getDBXrefs(
            final ResultSet rs,
            final String type
    ) {
        try {
            var identifier = rs.getString("accession");

            return new DBXrefsBean(
                    identifier,
                    type,
                    this.getUrl(type, identifier)
            );

        } catch (SQLException e) {
            log.error("Query is failed.", e);

            return null;
        }
    }

    public DBXrefsBean getDBXrefs(
            final String identifier,
            final String type
    ) {
        return new DBXrefsBean(
                identifier,
                type,
                this.getUrl(type, identifier)
        );
    }

    public BioLiveListBean getBioLiveList(final ResultSet rs) {
        try {
            var accession = rs.getString("accession");
            var status = rs.getString("status");
            var visibility = rs.getString("visibility");
            var dateCreated = null == rs.getTimestamp("date_created") ? null : rs.getTimestamp("date_created").toLocalDateTime();
            var datePublished = null == rs.getTimestamp("date_published") ? null : rs.getTimestamp("date_published").toLocalDateTime();
            var dateModified = null == rs.getTimestamp("date_modified") ? null : rs.getTimestamp("date_modified").toLocalDateTime();
            var json = rs.getString("json");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            var updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

            return new BioLiveListBean(
                    accession,
                    status,
                    visibility,
                    dateCreated,
                    datePublished,
                    dateModified,
                    json,
                    createdAt,
                    updatedAt
            );
        } catch (SQLException e) {
            log.error("Converting livelist is failed.", e);

            return null;
        }
    }

    public BioDateBean getBioDate(final ResultSet rs) {
        try {
            var accession = rs.getString("accession");
            var dateCreated = null == rs.getTimestamp("date_created") ? null : rs.getTimestamp("date_created").toLocalDateTime();
            var datePublished = null == rs.getTimestamp("date_published") ? null : rs.getTimestamp("date_published").toLocalDateTime();
            var dateModified = null == rs.getTimestamp("date_modified") ? null : rs.getTimestamp("date_modified").toLocalDateTime();

            return new BioDateBean(
                    accession,
                    dateCreated,
                    datePublished,
                    dateModified
            );
        } catch (SQLException e) {
            log.error("Converting bio's date is failed.", e);

            return null;
        }
    }

    public DraLiveListBean getDraLiveList(final ResultSet rs) {
        try {
            var accession = rs.getString("accession");
            var submission = rs.getString("submission");
            var visibility = rs.getString("visibility");
            var updated = null == rs.getTimestamp("updated") ? null : rs.getTimestamp("updated").toLocalDateTime();
            var type = rs.getString("type");
            var center = rs.getString("center");
            var alias = rs.getString("alias");
            var md5sum = rs.getString("md5sum");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            var updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

            return new DraLiveListBean(
                    accession,
                    submission,
                    visibility,
                    updated,
                    type,
                    center,
                    alias,
                    md5sum,
                    createdAt,
                    updatedAt
            );
        } catch (SQLException e) {
            log.error("Converting livelist is failed.", e);

            return null;
        }
    }

    public String xmlToJson(final String xml) {

        try {
            return XML.toJSONObject(xml).toString();

            // 非検査例外だがパース失敗をログ出力するようにした
        } catch (JSONException e) {
            var message = String.format("Converting xml to json is failed.: %s", xml);
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public String beanToJson(final Object bean) {

        try {
            return this.objectMapper.writeValueAsString(bean);

        } catch (JsonProcessingException e) {
            var message = "Converting bean to json is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public byte[] beanToByte(final Object bean) {

        try {
            var json = this.objectMapper.writeValueAsString(bean);

            return json.getBytes();
        } catch (JsonProcessingException e) {
            var message = "Converting bean to byte array is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public String paintPretty(final String json) {
        try {
            return this.objectMapper.writeValueAsString(this.objectMapper.readTree(json));
        } catch (JsonProcessingException e) {
            var message = "Painting pretty json is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }
}
