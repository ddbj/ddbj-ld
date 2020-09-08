package ddbjld.api.app.controller.api.v1.view;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.core.module.AuthModule;
import ddbjld.api.app.service.ProjectService;
import ddbjld.api.common.annotation.Auth;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.data.beans.ProjectMemberList;
import ddbjld.api.data.beans.ProjectMemberList.ProjectMember;
import ddbjld.api.data.beans.ProjectViewData;
import ddbjld.api.data.values.DraftMetadata;
import ddbjld.api.data.values.ProjectIds;


@Slf4j
@RestController
@RequestMapping({ 
	"view/project",
	"v1/view/project", 
})
public class ProjectController {
	
	@Autowired
	ConfigSet config;
	
	@Autowired
	AuthModule authorize;
	
	@Autowired
	ProjectService projectService;
	
	
	
	// ■プロジェクトの基本CRUD■
	
	/** プロジェクトの作成 */
	@PostMapping()
	@Auth
	public ProjectIds createProject(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestBody String body ) {
		
		UUID ownerAccountUUID = this.authorize.getAuthAccountUUID( request, true );
		
		ProjectIds ids = this.projectService.createProject( ownerAccountUUID );
		
		log.debug( "created project uuid[{}] id[{}]", ids.uuid, ids.id );
		
		return ids;
	}
	
	/** プロジェクトデータ取得 */
	@GetMapping("/{project-id}")
	// ※ 【重要】ログインしていない状態でも検索・参照が出来るのでAuthは付けない【重要】
	public ProjectViewData getProject(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId ) {
		
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request );
		
		ProjectViewData view = null == authAccountUUID
				? this.projectService.getDetailViewData( projectId )
				: this.projectService.getDetailViewData( projectId, authAccountUUID );
		
		return view;
	}
	
// TODO：【将来的対応の可能性】プロジェクトデータの分割取得に関しては、性能問題が出た時に検討する。（現状では素案）
//	/** プロジェクトデータ取得（分割：メニュー） */
//	@GetMapping("/{project-id}/menu/{menu-name}/metadata")
//	@Deprecated
//	Object getProjectMenu(
//			final HttpServletRequest request,
//			final HttpServletResponse response,
//			@PathVariable("project-id") String projectId,
//			@PathVariable("menu-name") String menuName ) {
//		return "";
//	}
//	
//	/** プロジェクトデータ取得（分割：タブ） */
//	@GetMapping("/{project-id}/menu/{menu-name}/tab/{tab-name}/metadata")
//	@Deprecated
//	Object getProjectTab(
//			final HttpServletRequest request,
//			final HttpServletResponse response,
//			@PathVariable("project-id") String projectId,
//			@PathVariable("menu-name") String menuName,
//			@PathVariable("tab-name") String tabName ) {
//		return "";
//	}
	
	
	
	
	// ■プロジェクト権限（関係者）の関連エンドポイント■
	
	/** プロジェクト関係者の一覧取得 */
	@Auth
	@GetMapping("/{project-id}/member")
	public ProjectMemberList listProjectMember(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId ) {
		
		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		var members = this.projectService.getProjectMemberList( projectId, authAccountUUID );
		
		return members;
	}
	
	/** 編集権限ユーザの追加（招待） */
	@PostMapping("/{project-id}/editor/invite/{account-uuid}")
	public ProjectMember inviteMemberAsEditor(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestBody String body,
			@PathVariable("project-id") String projectId,
			@PathVariable("account-uuid") UUID accountUUID ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		// 共同編集者として既存のAccountを招待。
		ProjectMember editor = this.projectService.inviteProjectEditor( projectId, accountUUID, authAccountUUID );
		// 作成したプロジェクト権限（関係者）データを返す。
		return editor;
	}
	
	// FIXME：ファーストリリースではDDBJアカウントの新規作成は行わない。
//	/** 編集権限ユーザの追加（新規作成） */
//	@PostMapping("/{project-id}/editor/new")
//	@Deprecated
//	Object createMemberAsEditor(
//			final HttpServletRequest request,
//			final HttpServletResponse response,
//			final @RequestBody String body,
//			@PathVariable("project-id") String projectId ) {
//		
//		return "";
//	}
	
	// FIXME：ファーストリリースでは閲覧用URLで機能代替する。
//	/** 閲覧権限ユーザの追加（招待） */
//	@PostMapping("/{project-id}/observer/invite/{account-id}")
//	@Deprecated
//	Object inviteMemberAsObserver(
//			final HttpServletRequest request,
//			final HttpServletResponse response,
//			final @RequestBody String body,
//			@PathVariable("project-id") String projectId,
//			@PathVariable("account-id") String accountId ) {
//
//		return "";
//	}
	
	// FIXME：ファーストリリースではDDBJアカウントの新規作成は行わない。
//	/** 閲覧権限ユーザの追加（新規作成） */
//	@PostMapping("/{project-id}/observer/new")
//	@Deprecated
//	Object createMemberAsObserver(
//			final HttpServletRequest request,
//			final HttpServletResponse response,
//			final @RequestBody String body,
//			@PathVariable("project-id") String projectId ) {
//
//		return "";
//	}
	
	
	/** 関係者の権限無効化 */
	@PostMapping("/{project-id}/member/{account-uuid}/suspend")
	@Auth
	public String suspendMember(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @PathVariable("project-id") String projectId,
			final @PathVariable("account-uuid") UUID accountUUID ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須

		int updated = this.projectService.updateProjectMemberEnabled( projectId, accountUUID, authAccountUUID, false );
		log.info( "updated ProjectRole [{}].", updated );
		return "updated"; //TODO：戻り値要検討。
	}
	
	/** 関係者の権限有効化 */
	@PostMapping("/{project-id}/member/{account-uuid}/activate")
	@Auth
	public String activateMember(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @PathVariable("project-id") String projectId,
			final @PathVariable("account-uuid") UUID accountUUID ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須

		int updated = this.projectService.updateProjectMemberEnabled( projectId, accountUUID, authAccountUUID, false );
		log.info( "updated ProjectRole [{}].", updated );
		return "updated"; //TODO：戻り値要検討。
	}

	/** 関係者の削除 */
	@DeleteMapping("/{project-id}/member/{account-uuid}")
	@Auth
	public String deleteMember(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @PathVariable("project-id") String projectId,
			final @PathVariable("account-uuid") UUID accountUUID ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		int deleted = this.projectService.deleteProjectMember( projectId, accountUUID, authAccountUUID );
		log.info( "deleted ProjectRole [{}].", deleted );
		return "deleted"; //TODO：戻り値要検討
	}
	
	//FIXME：ファーストリリースでは不要。（共同編集者は無期限固定、共同閲覧者は仕様凍結中のため、更新を掛けるケースが無くなる）
//	/** 関係者の権限更新 */
//	@PostMapping("/{project-id}/member/{account-id}")
//	@Deprecated
//	Object updateMember(
//			final HttpServletRequest request,
//			final HttpServletResponse response,
//			final @RequestBody String body,
//			final @PathVariable("project-id") String projectId,
//			final @PathVariable("account-id") String accountId ) {
//		return "";
//	}
	
	
	
	
	// FIXME：ファーストリリースでは不要。（初回は既存のアカウント紐付けしか無いので、招待が途中処理になる事がない）
//	/** 招待情報の一覧取得 */
//	@GetMapping("/{project-id}/invitation")
//	@Deprecated
//	Object listInvitation(
//			final HttpServletRequest request,
//			final HttpServletResponse response,
//			@PathVariable("project-id") String projectId ) {
//		return "";
//	}

	// FIXME：ファーストリリースでは不要。（初回は既存のアカウント紐付けしか無いので、招待が途中処理になる事がない）
//	/** 招待処理のリジューム */
//	@PostMapping("/{project-id}/invitation/{invitation-id}/resume")
//	@Deprecated
//	Object resumeInvitation(
//			final HttpServletRequest request,
//			final HttpServletResponse response,
//			@PathVariable("project-id") String projectId,
//			@PathVariable("invitation-id") String invitationId ) {
//		return "";
//	}
	
	
	
	
	
	// ■代替仕様：参照用URL発行■
	// TODO：【８月以降対応機能】※ ファーストリリースには乗せる必要がある
	
	/** 参照用URLの発行 */
	@PostMapping("/{project-id}/access-grant")
	@Deprecated
	Object createAccessGrant(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestBody String body,
			@PathVariable("project-id") String projectId ) {
		return "";
	}
	
	/** 参照用URLの削除 */
	@DeleteMapping("/{project-id}/access-grant")
	@Deprecated
	Object deleteAccessGrant(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId ) {
		return "";
	}
	
	/** 参照用URLの更新 */
	@PostMapping("/{project-id}/access-grant/{token}")
	@Deprecated
	Object updateAccessGrant(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestBody String body,
			@PathVariable("project-id") String projectId,
			@PathVariable("token") String token ) {
		return "";
	}
	
	/** 参照用URLの一覧取得 */
	@GetMapping("/{project-id}/access-grant")
	@Deprecated
	Object listAccessGrant(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId ) {
		return "";
	}
	
	
	
	
	// ■ドラフトデータ関連（プロジェクト編集）■
	
	/** 編集開始 */
	@PostMapping("/{project-id}/draft")
	@Auth
	public ProjectIds beginEditProject(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId ) {
		
		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		var ids = this.projectService.createDraftData( projectId, authAccountUUID );
		
		return ids;
	}
	
	/** 編集破棄 */
	@DeleteMapping("/{project-id}/draft")
	@Auth
	public ProjectIds revertEditProject(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		var ids = this.projectService.deleteDraftData( projectId, authAccountUUID );
		
		return ids;
	}
	
	/** 編集中メタデータの取得 */
	@GetMapping("/{project-id}/draft/metadata")
	@Auth
	public DraftMetadata getDraftMetadata(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		DraftMetadata data = this.projectService.getDraftMetadata( projectId, authAccountUUID );
		
		return data;
	}
	
	/** 編集中メタデータの更新（排他制御） */
	@PostMapping("/{project-id}/draft/metadata")
	@Auth
	public DraftMetadata postDraftMetadata(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestBody String body,
			@PathVariable("project-id") String projectId ) {
		
		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		DraftMetadata posted = DraftMetadata.parse( body );
		
		DraftMetadata data = this.projectService.updateDraftMetadata( projectId, authAccountUUID, posted );
		
		return data;
	}
	
	/** 編集中メタデータの取得（画面表示用） */
	@GetMapping("/{project-id}/draft/preview")
	@Auth
	public ProjectViewData previewDraftMetadata(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		ProjectViewData data = this.projectService.getDetailPreviewData( projectId, authAccountUUID );
		
		return data;
	}

	/** 公開・変更申請 */
	@PostMapping("/{project-id}/draft/publish")
	@Auth
	public long requestPublishProject(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestBody String body,
			@PathVariable("project-id") String projectId ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		long issuedNo = this.projectService.requestPublishProject( projectId, authAccountUUID );
		
		return issuedNo;
	}
	
	
	
	// ■プロジェクトの削除■　FIXME ※要件未定なので後回し。
	
	/** プロジェクトの削除依頼 */
	@DeleteMapping("/{project-id}")
	@Deprecated
	Object deleteProject(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId ) {
		
		throw new RestApiException( HttpStatus.NOT_IMPLEMENTED );
	}
	
	// ■メタデータの単体取得■ TODO ※ 画面用に用意しといたが特に要望来てないので消すかも。
	
	/** メタデータ取得 */
	@GetMapping("/{project-id}/metadata/{metadata-id}")
	@Deprecated
	Object getMetadata(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") String projectId,
			@PathVariable("metadata-id") String metadataId ) {
		
		throw new RestApiException( HttpStatus.NOT_IMPLEMENTED );
	}
}
