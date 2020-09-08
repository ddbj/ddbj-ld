package ddbjld.api.app.controller.api.v1.view;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.service.AuthService;
import ddbjld.api.data.beans.AccessTokenInfo;
import ddbjld.api.data.beans.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * ログイン処理・アクセストークン関係の処理といった認証のコントローラクラス.
 *
 * @author m.tsumura
 *
 **/
@RestController
@RequestMapping(path = {
		"v1/view/auth",
		"view/auth"
})
@Slf4j
public class AuthController {

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
	@PostMapping("/{code}/login")
	UserInfo getLoginUserInfo(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("code") String code ) {

		return authService.getLoginUserInfo(code);
	}

	/**
	 * アクセストークン更新API.
	 *
	 * <p>
	 * OpenAMのaccessToken APIを用い、アクセストークンを更新する<br>
	 * </p>
	 *
	 * @param accountUuid アカウントUUID
	 * @return アクセストークン
	 *
	 **/
	@PostMapping("/{accountUuid}/refresh")
	AccessTokenInfo updateAccessToken(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("accountUuid") UUID accountUuid ) {

		return authService.updateAccessToken(accountUuid);
	}
}
