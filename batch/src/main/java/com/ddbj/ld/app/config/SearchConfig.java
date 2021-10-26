package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-batch.properties", encoding = "UTF-8")
public class SearchConfig {
	public String hostname;
	public int port;
	public String scheme;
	public int socketTimeout;
	public String resourceUrl;
	public int maximumRecord;

	public SearchConfig(
			@Value( "${elastic-search.hostname}" ) String hostname,
			@Value( "${elastic-search.port}" ) int port,
			@Value( "${elastic-search.scheme}" ) String scheme,
			@Value( "${elastic-search.socket-timeout}" ) int socketTimeout,
			@Value( "${elastic-search.resource-url}" ) String resourceUrl,
			@Value( "${elastic-search.maximum-record}" ) int maximumRecord
	) {
		this.hostname = hostname;
		this.port = port;
		this.scheme = scheme;
		this.socketTimeout = socketTimeout;
		this.resourceUrl = resourceUrl;
		this.maximumRecord = maximumRecord;
	}
}
