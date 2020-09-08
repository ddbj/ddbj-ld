package ddbjld.api.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ddbjld.api.common.utility.UrlBuilder;

@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
public class OpenAMConfig {

	public static class OamEndpoints {
		public final String TOKEN_INFO;
		public final String ACCESS_TOKEN;
		public final String ADMIN_LOGIN;
		public final String USER_LIST;
		public final String USER;
		
		private OamEndpoints(
				final String baseUrl,
				final String tokenInfoPath,
				final String accessTokenPath,
				final String adminLoginPath,
				final String userListPath,
				final String userPath ) {
			this.TOKEN_INFO    = UrlBuilder.url( baseUrl ).path( tokenInfoPath ).build();
			this.ACCESS_TOKEN  = UrlBuilder.url( baseUrl ).path( accessTokenPath ).build();
			this.ADMIN_LOGIN   = UrlBuilder.url( baseUrl ).path( adminLoginPath ).build();
			this.USER_LIST     = UrlBuilder.url( baseUrl ).path( userListPath ).build();
			this.USER          = UrlBuilder.url( baseUrl ).path( userPath ).build();
		}
	}
	public static class OamClient {
		public final String REDIRECT_URL;
		public final String ID;
		public final String SECRET;
		public final String ADMIN;
		public final String ADMIN_PASS;
		
		private OamClient( String redirectUrl, String id, String secret, String admin, String adminPass ) {
			this.REDIRECT_URL = redirectUrl;
			this.ID = id;
			this.SECRET = secret;
			this.ADMIN = admin;
			this.ADMIN_PASS = adminPass;
		}
	}
	
	public final OamEndpoints endpoints;
	public final OamClient client;
	
	public OpenAMConfig(

			// OpenAM エンドポイント設定
			@Value( "${openam.endpoints.base-url}"             ) String openam_endpoints_base_url, 
			@Value( "${openam.endpoints.path.token-info}"      ) String openam_endpoints_token_info_path, 
			@Value( "${openam.endpoints.path.access-token}"    ) String openam_endpoints_access_token_path, 
			@Value( "${openam.endpoints.path.admin-login}"     ) String openam_endpoints_admin_login_path, 
			@Value( "${openam.endpoints.path.user-list}"       ) String openam_endpoints_user_list_path,
			@Value( "${openam.endpoints.path.user}"       ) String openam_endpoints_user_path,
			
			// OpenAM クライアント設定
			@Value( "${openam.client.redirect-url}"            ) String openam_client_redirect_url, 
			@Value( "${openam.client.id}"                      ) String openam_client_id, 
			@Value( "${openam.client.secret}"                  ) String openam_client_secret, 
			@Value( "${openam.client.admin}"                   ) String openam_client_admin, 
			@Value( "${openam.client.admin-password}"          ) String openam_client_admin_pass
	) {
		this.endpoints = new OamEndpoints( 
				openam_endpoints_base_url, 
				openam_endpoints_token_info_path, 
				openam_endpoints_access_token_path, 
				openam_endpoints_admin_login_path, 
				openam_endpoints_user_list_path,
				openam_endpoints_user_path);
		
		this.client = new OamClient( 
				openam_client_redirect_url, 
				openam_client_id,
				openam_client_secret, 
				openam_client_admin, 
				openam_client_admin_pass );
	}
}
