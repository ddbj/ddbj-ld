package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-batch.properties", encoding = "UTF-8")
public class ElasticSearchConfig {
	public String hostname;
	public int port;
	public String scheme;
	public int socketTimeout;

	public ElasticSearchConfig(
			@Value( "${elastic-search.hostname}" ) String hostname,
			@Value( "${elastic-search.port}" ) int port,
			@Value( "${elastic-search.scheme}" ) String scheme,
			@Value( "${elastic-search.socket-timeout}" ) int socketTimeout
	) {
		this.hostname = hostname;
		this.port = port;
		this.scheme = scheme;
		this.socketTimeout = socketTimeout;
	}
}
