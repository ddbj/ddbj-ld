package ddbjld.api.app.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ConfigSet {
	
	public ApiConfig api;
	
	public OpenAMConfig openam;

	public NextCloudConfig nextcloud;
	
	public ElasticSearchConfig elasticsearch;

	public JsonLdConfig jsonLdConfig;
}