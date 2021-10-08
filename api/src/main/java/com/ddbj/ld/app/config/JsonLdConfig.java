package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
public class JsonLdConfig {

	// 現状、ベースURLしか設定情報が無いが、
	// 設定情報が増えたときに備えて他のConfigと構成を似せておく。

	public final String url;

	public JsonLdConfig(@Value("${jsonld.schema.url}") String schema_url ) {
		this.url = schema_url;
	}
}
