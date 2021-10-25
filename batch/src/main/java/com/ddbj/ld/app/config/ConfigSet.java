package com.ddbj.ld.app.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class ConfigSet {
    // 外部で使う設定値をまとめておくクラス、Configでも外部で設定値を使わないクラスは記載しない

    public ElasticSearchConfig elasticsearch;

    public FileConfig file;

    public MessageConfig message;

    public OtherConfig other;
}
