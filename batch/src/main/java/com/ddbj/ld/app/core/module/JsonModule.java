package com.ddbj.ld.app.core.module;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.common.annotation.Module;
import com.ddbj.ld.common.constants.DistributionEnum;
import com.ddbj.ld.data.beans.common.DistributionBean;
import com.ddbj.ld.data.beans.common.OrganismBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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

    public String getUrl(
            final String type,
            final String identifier
    ) {
        return this.config.other.resourceUrl + type + "/" + identifier;
    }

    public String getUrl(
            final String type,
            final String identifier,
            final String extension
    ) {
        return this.config.other.resourceUrl + type + "/" + identifier + extension;
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
            return esSimpleDateFormat.format(timestamp);
        } catch(Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    public String parseOffsetDateTime(
            final OffsetDateTime timestamp
    ) {
        try {
            return timestamp.format(this.esFormatter);
        } catch(Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    public String parseLocalDate(
            final LocalDate localDate
    ) {
        try {
            return this.esSimpleDateFormat.format(Timestamp.valueOf(localDate.atStartOfDay()));
        } catch(Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
