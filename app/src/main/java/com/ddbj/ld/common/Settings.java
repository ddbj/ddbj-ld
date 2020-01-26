package com.ddbj.ld.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "settings")
@Data
public class Settings {
    private String bioProjectXml;
    private String bioSampleXml;
    private String xmlPath;
    private String tsvPath;
    private int maximumRecord;
    private String timeStampFormat;
    private String hostname;
    private int port;
    private String scheme;
    private String mode;
    private String developmentRecordNumber;
}

