package ddbjld.api.app.core.module;

import ddbjld.api.app.config.ConfigSet;
<<<<<<< HEAD
import ddbjld.api.common.annotation.Module;
import ddbjld.api.common.constants.ApiMethod;
=======
import ddbjld.api.common.exceptions.RestApiException;
>>>>>>> 差分修正
import ddbjld.api.common.utility.HeaderUtil;
import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.common.utility.StringUtil;
import ddbjld.api.common.utility.api.StandardRestClient;
import ddbjld.api.data.beans.*;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

@Module
@AllArgsConstructor
@Slf4j
public class AuthModule {
	private ConfigSet config;
	
	public boolean requireSecretKey(final HttpServletRequest request) {
		String header = HeaderUtil.getSecretKey(request);

		if (null == header) {
			log.error("X-MB-Secret header is null.");

			return false;
		}
=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class AuthModule {
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
>>>>>>> 差分修正
		
		String[] token = header.trim().split( " " );
		String phrase = StringUtil.join( ".", token );
		UUID secret = StringUtil.uuidv3( phrase );
		
		// application.properties に設定しているシークレットキーと一致する場合はOK
<<<<<<< HEAD
		return this.config.system.secretKey.equals(secret);
=======
		if ( this.ddbjSecretKey.equals( secret ) ) {
			return;
		}
		// シークレットキーが一致しない場合はNG
		else {
			throw new RestApiException( HttpStatus.UNAUTHORIZED );
		}
>>>>>>> 差分修正
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
	public LoginInfo getToken(final UUID code) {
<<<<<<< HEAD
		var client  = new StandardRestClient();
		var headers = new HashMap<String, String>();
		headers.put("Authorization", "Basic " + StringUtil.base64(config.openam.client.ID + ":" + config.openam.client.SECRET));
		var body = "grant_type=authorization_code&code=" + code + "&redirect_uri=" + config.openam.client.REDIRECT_URL;
		var api = client.exchange(config.openam.endpoints.ACCESS_TOKEN, ApiMethod.POST_X_FORM_URLENCODED, headers, body);

		if(api.response.not2xxSuccessful()) {
			log.error("OpenAM API Error {} {}"
					, api.response.status
					, api.response.text);

			return null;
		}

		return JsonMapper.parse(api.response.body, LoginInfo.class);
=======
		var endpoints = config.openam.endpoints;
		var client = config.openam.client;

		MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
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
>>>>>>> 差分修正
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
<<<<<<< HEAD
		var client = new StandardRestClient();
		var api    = client.get(config.openam.endpoints.TOKEN_INFO + accessToken);

		if(api.response.not2xxSuccessful()) {
			log.error( "OpenAM API Error {} {}"
					, api.response.status
					, api.response.text );

=======
		var endpoints = config.openam.endpoints;
		final String url = endpoints.TOKEN_INFO + accessToken;

		var client = new StandardRestClient();
		var api    = client.get(url);

		if(api.response.not2xxSuccessful()) {
>>>>>>> 差分修正
			return null;
		}

		return JsonMapper.parse(api.response.body, TokenInfo.class);
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
<<<<<<< HEAD
		var client  = new StandardRestClient();
		var body    = "grant_type=refresh_token&refresh_token=" + refreshToken + "&client_id=" + config.openam.client.ID + "&client_secret=" + config.openam.client.SECRET;
		var api     = client.exchange(config.openam.endpoints.ACCESS_TOKEN, ApiMethod.POST_X_FORM_URLENCODED, null, body);

		if(api.response.not2xxSuccessful()) {
			log.error("OpenAM API Error {} {}"
					, api.response.status
					, api.response.text);

			return null;
		}

		return JsonMapper.parse(api.response.body, LoginInfo.class);
=======
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
>>>>>>> 差分修正
	}

	/**
	 * OpenAMの管理者ユーザでログインするメソッド.
	 *
	 * <p>
	 * OpenAMの「/json/realms/root/authenticate」 APIを用い、管理者ユーザーでログインする。<br>
<<<<<<< HEAD
	 * 返却される管理者ユーザー情報の中にあるtokenIDを用いてOpenAMの管理APIを利用することができる。
	 * </p>
	 *
	 * @return 管理者ユーザー情報
	 *
	 **/
	public AmAdminInfo loginAdmin() {
		var client  = new StandardRestClient();
		var headers = new HashMap<String, String>();
		headers.put("X-OpenAM-Username", config.openam.client.ADMIN);
		headers.put("X-OpenAM-Password", config.openam.client.ADMIN_PASS);
		var api = client.exchange(config.openam.endpoints.ADMIN_LOGIN, ApiMethod.POST_JSON, headers, null);

		if(api.response.not2xxSuccessful()) {
			log.error("OpenAM API Error {} {}"
					, api.response.status
					, api.response.text);

			return null;
		}

		return JsonMapper.parse(api.response.body, AmAdminInfo.class);
=======
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
>>>>>>> 差分修正
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
<<<<<<< HEAD
	 * @return OpenAMのユーザのリスト
	 *
	 **/
	public AmResultInfo getAmUserList(String tokenId) {
		var client  = new StandardRestClient();
		var headers = new HashMap<String, String>();
		headers.put("iplanetDirectoryPro", tokenId);
		var api = client.exchange(config.openam.endpoints.USER_LIST, ApiMethod.GET, headers, null);

		if(api.response.not2xxSuccessful()) {
			log.error("OpenAM API Error {} {}"
					, api.response.status
					, api.response.text);

			return null;
		}

		return JsonMapper.parse(api.response.body, AmResultInfo.class);
=======
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
>>>>>>> 差分修正
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
<<<<<<< HEAD
		var client  = new StandardRestClient();
		var headers = new HashMap<String, String>();
		headers.put("iplanetDirectoryPro", tokenId);
		var api = client.exchange(config.openam.endpoints.USER + uid, ApiMethod.GET, headers, null);

		if(api.response.not2xxSuccessful()) {
			log.error("OpenAM API Error {} {}"
					, api.response.status
					, api.response.text);

			return null;
		}

		return JsonMapper.parse(api.response.body, AmUserInfo.class);
=======
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
>>>>>>> 差分修正
	}

	/**
	 * アクセストークンの有効性を判定するメソッド.
	 *
	 * <p>
	 * OpenAMのtokeninfo APIを用い、アクセストークンを判定する。<br>
<<<<<<< HEAD
=======
	 * トランザクションは発生しない処理だが、Springに登録されたrestTemplateを使用するため、Serviceクラスに配置<br>
>>>>>>> 差分修正
	 * </p>
	 *
	 * @param accessToken
	 *
	 * @return 判定結果
	 *
	 **/
	public boolean isAuth(final String accessToken) {
<<<<<<< HEAD
		var client = new StandardRestClient();
		var api    = client.get(config.openam.endpoints.TOKEN_INFO + accessToken);
=======
		var endpoints = config.openam.endpoints;
		final String url = endpoints.TOKEN_INFO + accessToken;

		var client = new StandardRestClient();
		var api    = client.get(url);
>>>>>>> 差分修正

		return api.response.is2xxSuccessful();
	}
}
