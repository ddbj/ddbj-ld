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
		public final String ROOT_JVAR;
		public final String ROOT_BIOPROJECT;
		public final String ROOT_BIOSAMPLE;
		public final String ROOT_TRAD;

		private NCloudEndpoints(
				final String baseUrl,
				final String admin,
				final String rootJVar,
				final String rootBioProject,
				final String rootBioSample,
				final String rootTrad
				) {
			this.URL             = UrlBuilder.url( baseUrl ).path( admin ).build();
			this.ROOT_JVAR       = rootJVar;
			this.ROOT_BIOPROJECT = rootBioProject;
			this.ROOT_BIOSAMPLE  = rootBioSample;
			this.ROOT_TRAD       = rootTrad;
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
			@Value( "${nextcloud.endpoints.root.jvar}"             ) String nextcloud_endpoints_root_jvar,
			@Value( "${nextcloud.endpoints.root.bioproject}"             ) String nextcloud_endpoints_root_bioproject,
			@Value( "${nextcloud.endpoints.root.biosample}"             ) String nextcloud_endpoints_root_biosample,
			@Value( "${nextcloud.endpoints.root.trad}"             ) String nextcloud_endpoints_root_trad,
			
			// NextCloud クライアント設定
			@Value( "${nextcloud.client.admin}"                   ) String nextcloud_client_admin,
			@Value( "${nextcloud.client.admin-password}"          ) String nextcloud_client_admin_pass
	) {
		this.endpoints = new NCloudEndpoints(
				nextcloud_endpoints_base_url,
				nextcloud_client_admin,
				nextcloud_endpoints_root_jvar,
				nextcloud_endpoints_root_bioproject,
				nextcloud_endpoints_root_biosample,
				nextcloud_endpoints_root_trad
		);
		
		this.client = new NCloudClient(
				nextcloud_client_admin,
				nextcloud_client_admin_pass);
	}
}
