package ddbjld.api.app.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class ConfigSet {
	// 外部で使う設定値をまとめておくクラス、Configでも外部で設定値を使わないクラスは記載しない

	public ApiConfig api;

	public OpenAMConfig openam;

	public ElasticSearchConfig elasticsearch;

	public JsonLdConfig jsonLd;

	public FileConfig file;

	public ValidationConfig validation;

	public SystemConfig system;
}
