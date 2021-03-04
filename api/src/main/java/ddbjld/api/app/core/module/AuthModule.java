package ddbjld.api.app.core.module;

import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.common.annotation.Module;
import ddbjld.api.common.constants.ApiMethod;
import ddbjld.api.common.utility.HeaderUtil;
import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.common.utility.StringUtil;
import ddbjld.api.common.utility.api.StandardRestClient;
import ddbjld.api.data.beans.*;
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
		
		String[] token = header.trim().split( " " );
		String phrase = StringUtil.join( ".", token );
		UUID secret = StringUtil.uuidv3( phrase );
		
		// application.properties に設定しているシークレットキーと一致する場合はOK
		return this.config.system.secretKey.equals(secret);
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
		var client = new StandardRestClient();
		var api    = client.get(config.openam.endpoints.TOKEN_INFO + accessToken);

		if(api.response.not2xxSuccessful()) {
			log.error( "OpenAM API Error {} {}"
					, api.response.status
					, api.response.text );

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
	}

	/**
	 * OpenAMの管理者ユーザでログインするメソッド.
	 *
	 * <p>
	 * OpenAMの「/json/realms/root/authenticate」 APIを用い、管理者ユーザーでログインする。<br>
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
	}

	/**
	 * アクセストークンの有効性を判定するメソッド.
	 *
	 * <p>
	 * OpenAMのtokeninfo APIを用い、アクセストークンを判定する。<br>
	 * </p>
	 *
	 * @param accessToken
	 *
	 * @return 判定結果
	 *
	 **/
	public boolean isAuth(final String accessToken) {
		var client = new StandardRestClient();
		var api    = client.get(config.openam.endpoints.TOKEN_INFO + accessToken);

		return api.response.is2xxSuccessful();
	}
}
