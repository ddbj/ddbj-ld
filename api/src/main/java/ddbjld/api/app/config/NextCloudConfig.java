package ddbjld.api.app.config;

import ddbjld.api.common.utility.UrlBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
public class NextCloudConfig {

	public static class NCloudEndpoints {
		public final String URL;

		private NCloudEndpoints(
				final String baseUrl,
				final String admin) {
			this.URL    = UrlBuilder.url( baseUrl ).path( admin ).build();
		}
	}

	public static class NCloudClient {
		public final String ADMIN;
		public final String ADMIN_PASS;

		private NCloudClient( String admin, String adminPass ) {
			this.ADMIN = admin;
			this.ADMIN_PASS = adminPass;
		}
	}

	public final NCloudEndpoints endpoints;
	public final NCloudClient client;

	public NextCloudConfig(

			// NextCloud エンドポイント設定
			@Value( "${nextcloud.endpoints.base-url}"             ) String nextcloud_endpoints_base_url,
			
			// NextCloud クライアント設定
			@Value( "${nextcloud.client.admin}"                   ) String nextcloud_client_admin,
			@Value( "${nextcloud.client.admin-password}"          ) String nextcloud_client_admin_pass
	) {
		this.endpoints = new NCloudEndpoints(
				nextcloud_endpoints_base_url,
				nextcloud_client_admin);
		
		this.client = new NCloudClient(
				nextcloud_client_admin,
				nextcloud_client_admin_pass);
	}
}
