package ddbjld.api.app.core.module;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.common.utility.StringUtil;
import ddbjld.api.data.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import ddbjld.api.app.service.AuthService;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.HeaderUtil;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class AuthModule {
	@Autowired
	AuthService service;

	@Autowired
	ConfigSet config;

	@Autowired
	private RestTemplate restTemplate; //TODO：後でRestClient部品に差し替え。
	
	
	// FIXME：後でConfigSetにSystemConfig作って移動。
	@Value("${ddbj.system.maintenance.secret.key}")
	UUID ddbjSecretKey;
	
	public void requireSecretKey( final HttpServletRequest request ) {
		String header = HeaderUtil.getMtabobankSecretKey( request );
		if ( null == header ) throw new RestApiException( HttpStatus.UNAUTHORIZED ); 
		
		String[] token = header.trim().split( " " );
		String phrase = StringUtil.join( ".", token );
		UUID secret = StringUtil.uuidv3( phrase );
		
		// application.properties に設定しているシークレットキーと一致する場合はOK
		if ( this.ddbjSecretKey.equals( secret ) ) {
			return;
		}
		// シークレットキーが一致しない場合はNG
		else {
			throw new RestApiException( HttpStatus.UNAUTHORIZED );
		}
	}
	
	
	
	public UUID getAuthAccountUUID( final HttpServletRequest request ) {
		return getAuthAccountUUID( request, false );
	}
	public UUID getAuthAccountUUID( final HttpServletRequest request, final boolean required ) {
		
		// 認証ヘッダ Authorization Berer が設定されている場合は、アクセストークンからアカウント情報を取得。
		var header = HeaderUtil.getAuthorization( request );
		if ( HeaderUtil.existsAuthorizationHeader( header ) ) {
			
			String accessToken = HeaderUtil.getAccessToken( header );
			UUID authAccountUUID = this.service.getAccountUUID( accessToken );
			
			
			// 認証ヘッダが付いているのにAccount情報が取れない場合は 401 エラー。
			if ( null == authAccountUUID ) throw new RestApiException( HttpStatus.UNAUTHORIZED );
			
			return authAccountUUID;
		}
		// 認証ヘッダなし（非ログイン状態での機能使用）の場合はアカウント情報なし。
		else {
			
			// アカウント情報必須なのに認証ヘッダがない場合は 400 エラー。
			if ( required ) throw new RestApiException( HttpStatus.BAD_REQUEST );
			
			return null;
		}
	}

	/**
	 * OpenAMに格納されたアクセストークン、リフレッシュトークンを取得するメソッド.
	 *
	 * <p>
	 * OpenAMのaccessToken APIを用い、取得する｡<br>
	 * </p>
	 *
	 * @param code
	 *
	 * @return OpenAMに格納されたユーザの情報
	 *
	 **/
	public LoginInfo getToken(final String code) {
		var endpoints = config.openam.endpoints;
		var client = config.openam.client;

		MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("code", code);
		body.add("redirect_uri", client.REDIRECT_URL);

		RequestEntity request = RequestEntity.post(URI.create(endpoints.ACCESS_TOKEN))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.header("Authorization", "Basic " + StringUtil.base64(client.ID + ":" + client.SECRET))
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(body);

		try {
			return restTemplate.exchange(request, LoginInfo.class).getBody();
		} catch (RestClientException e) {
			log.debug(e.getMessage());

			return null;
		}
	}

	/**
	 * アクセストークンからユーザー情報(uid, email)を取得するメソッド.
	 *
	 * <p>
	 * OpenAMのtokeninfo APIを用い、ユーザー情報(uid, email)を取得する｡<br>
	 * トランザクションは発生しない処理だが、Springに登録されたRestTemplateを使用するため、Serviceクラスに配置。
	 * </p>
	 *
	 * @param accessToken
	 *
	 * @return OpenAMから取得したユーザの情報
	 *
	 **/
	public TokenInfo getTokenInfo(final String accessToken) {
		var endpoints = config.openam.endpoints;
		final String url = endpoints.TOKEN_INFO + accessToken;

		try {
			return restTemplate.getForObject(url, TokenInfo.class);
		} catch (RestClientException e) {
			// 401が返ったらExceptionが返る
			return null;
		}
	}

	/**
	 * リフレッシュトークンからアクセストークン、リフレッシュトークンを再発行するメソッド.
	 *
	 * <p>
	 * OpenAMのaccessToken APIを用い、再発行する｡<br>
	 * </p>
	 *
	 * @param refreshToken
	 *
	 * @return OpenAMに格納されたユーザの情報
	 *
	 **/
	public LoginInfo getNewToken(final String refreshToken) {
		var endpoints = config.openam.endpoints;
		var client = config.openam.client;

		MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
		body.add("client_id", client.ID);
		body.add("client_secret", client.SECRET);
		body.add("grant_type", "refresh_token");
		body.add("refresh_token", refreshToken);

		RequestEntity request = RequestEntity.post(URI.create(endpoints.ACCESS_TOKEN))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(body);

		try {
			return restTemplate.exchange(request, LoginInfo.class).getBody();
		} catch (RestClientException e) {
			log.debug(e.getMessage());

			return null;
		}
	}

	/**
	 * OpenAMの管理者ユーザでログインするメソッド.
	 *
	 * <p>
	 * OpenAMの「/json/realms/root/authenticate」 APIを用い、管理者ユーザーでログインする。<br>
	 * </p>
	 *
	 * @return 管理者ユーザーのtokenId
	 *
	 **/
	public String loginAdmin() {
		var endpoints = config.openam.endpoints;
		var client = config.openam.client;

		RequestEntity request = RequestEntity.post(URI.create(endpoints.ADMIN_LOGIN))
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-OpenAM-Username", client.ADMIN)
				.header("X-OpenAM-Password", client.ADMIN_PASS)
				.build();

		try {
			AmAdminInfo amAdminInfo = restTemplate.exchange(request, AmAdminInfo.class).getBody();

			return amAdminInfo.getTokenId();
		} catch (RestClientException e) {
			log.debug(e.getMessage());

			return null;
		}
	}

	/**
	 * OpenAMのユーザのリストを取得するメソッド.
	 *
	 * <p>
	 * OpenAMの「/json/realms/root/users?_queryid=」 APIを用い、ユーザーのリストを取得する。<br>
	 * </p>
	 *
	 * @param tokenId
	 *
	 * @return 管理者ユーザーのトークン
	 *
	 **/
	public List<AmUserInfo> getAmUserList(String tokenId) {
		var endpoints = config.openam.endpoints;

		RequestEntity request = RequestEntity.get(URI.create(endpoints.USER_LIST))
				.header("iplanetDirectoryPro", tokenId)
				.build();

		try {
			AmResultInfo amUserInfo = restTemplate.exchange(request, AmResultInfo.class).getBody();

			return amUserInfo.getResult();
		} catch (RestClientException e) {
			log.debug(e.getMessage());

			return null;
		}
	}

	/**
	 * OpenAMのユーザのリストを取得するメソッド.
	 *
	 * <p>
	 * OpenAMの「/json/realms/root/users/[uid]?_queryid=」 APIを用い、ユーザーを取得する。<br>
	 * </p>
	 *
	 * @param tokenId
	 *
	 * @return 管理者ユーザーのトークン
	 *
	 **/
	public AmUserInfo getAmUser(String tokenId, String uid) {
		var endpoints = config.openam.endpoints;
		var url       = endpoints.USER + uid;

		RequestEntity request = RequestEntity.get(URI.create(url))
				.header("iplanetDirectoryPro", tokenId)
				.build();

		try {
			return restTemplate.exchange(request, AmUserInfo.class).getBody();
		} catch (RestClientException e) {
			log.debug(e.getMessage());

			return null;
		}
	}
}
