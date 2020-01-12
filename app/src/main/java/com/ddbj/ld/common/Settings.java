package com.ddbj.ld.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "settings")
@Data
public class Settings {
    private String xmlPath;
    private String jsonPath;
    private String tsvPath;
    private int maximumRecord;
    private String timeStampFormat;
}

