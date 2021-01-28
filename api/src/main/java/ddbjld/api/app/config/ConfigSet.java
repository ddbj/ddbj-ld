package ddbjld.api.app.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ConfigSet {

	public ApiConfig api;

	public OpenAMConfig openam;

	public ElasticSearchConfig elasticsearch;

	// FIXME 変数名を簡略化する
	public JsonLdConfig jsonLdConfig;

	public FileConfig file;

	public ValidationConfig validation;

	public SystemConfig system;
}