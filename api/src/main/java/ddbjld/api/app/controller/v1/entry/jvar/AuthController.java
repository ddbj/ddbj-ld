package ddbjld.api.app.controller.v1.entry.jvar;

import ddbjld.api.app.transact.service.AuthService;
import ddbjld.api.data.response.v1.entry.jvar.TokenResponse;
import ddbjld.api.data.response.v1.entry.jvar.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * ログイン処理・アクセストークン関係の処理といった認証のコントローラクラス.
 *
 * @author m.tsumura
 *
 **/
@RestController
@Slf4j
public class AuthController implements AuthApi {

	@Autowired
	private AuthService authService;
	
	/**
	 * ログインユーザ情報取得API.
	 *
	 * OpenAMから発行されたログインコードを元にアクセストークンとユーザ情報を取得
	 * DDBJに保存されているユーザーの情報を付与して返す
	 *
	 * @param code OpenAMから発行されたログインコード
	 * @return ログインユーザー情報
	 *
	 **/
	@Override
	public ResponseEntity<UserResponse> getLoginUserInfo( @PathVariable("code") UUID code ) {
		var response = this.authService.getLoginUserInfo(code);

		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		var status = null == response ? HttpStatus.UNAUTHORIZED : HttpStatus.OK;

		return new ResponseEntity<UserResponse>(response, headers, status);
	}

	/**
	 * アクセストークン更新API.
	 *
	 * <p>
	 * OpenAMのaccessToken APIを用い、アクセストークンを更新する<br>
	 * </p>
	 *
	 * @param accountUUID アカウントUUID
	 * @return アクセストークン
	 *
	 **/
	@Override
	public ResponseEntity<TokenResponse> refreshAccessToken(@PathVariable("accountUUID") UUID accountUUID) {
		var response = this.authService.refreshAccessToken(accountUUID);

		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		var status = null == response ? HttpStatus.UNAUTHORIZED : HttpStatus.OK;

		return new ResponseEntity<TokenResponse>(response, headers, status);
	}
}
