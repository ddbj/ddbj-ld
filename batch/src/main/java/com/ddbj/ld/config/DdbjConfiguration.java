package com.ddbj.ld.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class DdbjConfiguration {
    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSSS");
    }

    @Bean
    public SimpleDateFormat esSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");
    }
}
