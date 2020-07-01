package com.ddbj.ld.common.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "settings")
@Data
public class Settings {
    private String bioProjectPath;
    private String bioSamplePath;
    private String draXmlPath;
    private String jgaXmlPath;
    private String tsvPath;
    private String csvPath;
    private int maximumRecord;
    private String timeStampFormat;
    private String hostname;
    private int port;
    private String scheme;
}

