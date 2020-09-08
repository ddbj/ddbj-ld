package ddbjld.api.app.controller.api.v1.view;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.core.module.AuthModule;
import ddbjld.api.app.service.AuthService;
import ddbjld.api.common.annotation.Auth;
import ddbjld.api.data.beans.InvitationTargetInfo;
import ddbjld.api.data.beans.ProfileInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * DDBJシステム内でのユーザ情報を操作するコントローラ.
 *
 * @author m.tsumura
 *
 **/
@RestController
@RequestMapping(path = {
		"v1/view/user",
		"view/user"
})
@Slf4j
public class UserController {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthModule authModule;

	/**
	 * 既存ユーザ招待API.
	 *
	 * <p>
	 * 既にDDBJのアカウント管理システムで登録されているユーザを招待する<br>
	 * </p>
	 *
	 * @param projectId 招待対象となるプロジェクトのID
	 *
	 * @return void
	 *
	 **/
	@PostMapping("{project-id}/invite")
	@Auth
	@Deprecated
	void inviteProject(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId,
			@RequestBody final List<InvitationTargetInfo> targets) {
		UUID authAccountUUID = authModule.getAuthAccountUUID(request);
		this.authService.inviteProject(authAccountUUID, projectId, targets);
	}

	/**
	 * プロフィール更新API.
	 *
	 * <p>
	 * 既にDDBJのアカウント管理システムで登録されているユーザを招待する<br>
	 * </p>
	 *
	 * @param accountUuid ログインユーザーのUUID
	 *
	 * @return void
	 *
	 **/
	@PostMapping("{account-uuid}/profile")
	@Auth
	@Deprecated
	void updateProfile(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("account-uuid") final UUID accountUuid,
			@RequestBody final ProfileInfo profileInfo) {
		this.authService.updateProfile(accountUuid, profileInfo);
	}
}