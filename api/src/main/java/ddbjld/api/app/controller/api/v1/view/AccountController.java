package ddbjld.api.app.controller.api.v1.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.core.module.AuthModule;
import ddbjld.api.app.service.AuthService;
import ddbjld.api.common.annotation.Auth;
import ddbjld.api.data.beans.InvitationInfo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * DDBJアカウント（OpenAM／OpenDJ）に対するコントローラクラス.
 *
 * @author m.tsumura
 *
 **/
@RestController
@RequestMapping(path = {
		"v1/view/account",
		"view/account"
})
@Slf4j
public class AccountController {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthModule authModule;

	/**
	 * 招待ユーザ一覧取得API.
	 *
	 * <p>
	 * OpenAMのAPI「/json/realms/root/users?_queryid=」を用い、招待対象のユーザの一覧を取得する<br>
	 * </p>
	 *
	 * @param projectId 招待対象となるプロジェクトのID
	 *
	 * @return 招待ユーザー一覧
	 *
	 **/
	@GetMapping("{project-id}/invite/list")
	@Auth
	@Deprecated
	List<InvitationInfo> getInvitationList(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId) {
		UUID authAccountUUID = authModule.getAuthAccountUUID(request);

		return authService.getInvitationList(authAccountUUID, projectId);
	}
}
