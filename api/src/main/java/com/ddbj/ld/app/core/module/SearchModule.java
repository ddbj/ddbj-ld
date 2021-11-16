package com.ddbj.ld.app.core.module;

import com.ddbj.ld.common.annotation.Module;
import com.ddbj.ld.common.constant.ApiMethod;
import com.ddbj.ld.common.utility.UrlBuilder;
import com.ddbj.ld.common.utility.api.RestClient;
import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.common.exception.RestApiException;
import com.ddbj.ld.common.utility.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.*;

@Module
@AllArgsConstructor
@Slf4j
public class SearchModule {

	private final ConfigSet config;

	private final LinkedHashMap<String, Object> context;

	public LinkedHashMap<String, Object> get(final String type, final String identifier) {
		var url = UrlBuilder.url(this.config.elasticsearch.baseUrl, type, "_doc", identifier).build();
		var restClient = new RestClient();

		var result = restClient.exchange(url, ApiMethod.GET, null, null);

		if(result.response.status == 200) {
			// 何もしない
		} else {
			throw new RestApiException(HttpStatus.valueOf(result.response.status));
		}

		var map = JsonMapper.parse(result.response.body, new TypeReference<LinkedHashMap<String, Object>>() {
		});

		if(map.containsKey("_source")) {
			return (LinkedHashMap<String, Object>)map.get("_source");
		} else {
			throw new RestApiException(HttpStatus.NOT_FOUND);
		}
	}

	public LinkedHashMap<String, Object> getContext() {
		return this.context;
	}
}
