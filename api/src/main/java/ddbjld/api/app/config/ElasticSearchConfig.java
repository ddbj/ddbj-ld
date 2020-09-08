package ddbjld.api.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
public class ElasticSearchConfig {
	
	// 現状、ベースURLしか設定情報が無いが、
	// 設定情報が増えたときに備えて他のConfigと構成を似せておく。
	
	public final String baseUrl;

	public ElasticSearchConfig( @Value("${elastic-search.endpoints.base-url}") String endpoints_base_url ) {
		this.baseUrl = endpoints_base_url;
	}
}
