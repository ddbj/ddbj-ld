package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-batch.properties", encoding = "UTF-8")
public class OtherConfig {

    public int maximumRecord;
    public String timestampFormat;

    public OtherConfig(
            @Value( "${other.maximum-record}" ) int maximumRecord,
            @Value( "${other.timestamp-format}" ) String timestampFormat
    ) {
        this.maximumRecord = maximumRecord;
        this.timestampFormat = timestampFormat;
    }
}
