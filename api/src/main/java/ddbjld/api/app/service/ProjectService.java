package ddbjld.api.app.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.core.module.ElasticSearchModule;
import ddbjld.api.app.core.module.ElasticSearchModule.MetadataExtract.ExtractUnits;
import ddbjld.api.app.service.FileService;
import ddbjld.api.app.service.dao.DraftMetadataDao;
import ddbjld.api.app.service.dao.DraftMetadataDao.TDraftMetadataDataReader;
import ddbjld.api.app.service.dao.ProjectDao;
import ddbjld.api.app.service.dao.ProjectDao.TProjectDataReader;
import ddbjld.api.app.service.dao.ProjectRoleDao;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.common.utility.SpringJdbcUtil.MapDataReader;
import ddbjld.api.common.utility.SpringJdbcUtil.MapQuery;
import ddbjld.api.common.utility.data.MetadataAccessor;
import ddbjld.api.common.utility.data.MetadataAccessor.DataRowReader;
import ddbjld.api.common.utility.data.MetadataSummarizer;
import ddbjld.api.data.beans.ProjectMemberList;
import ddbjld.api.data.beans.ProjectMemberList.ProjectMember;
import ddbjld.api.data.beans.ProjectViewData;
import ddbjld.api.data.json.AggregateJson;
import ddbjld.api.data.json.ExcelSheetData;
import ddbjld.api.data.json.ListdataJson;
import ddbjld.api.data.json.MetadataJson;
import ddbjld.api.data.values.DraftMetadata;
import ddbjld.api.data.values.ProjectIds;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class ProjectService {


	@Autowired
	ElasticSearchModule elasticsearch;


	// FIXME：【要修正】FileService
	// ServiceからServiceの横呼び出しは厳禁、今は余裕がないので一旦これで行くが後で修正する。
	// Serviceに限らず、DIで循環参照が発生するとマズいので論理レイヤの横呼び出しは原則禁止。
	// FileServiceは性質的にはDAO相当なので、strageパッケージを新設して一段回落とした方が良いかも。
	@Autowired
	FileService fileService;


	@Autowired @Qualifier("jvarJdbc")
	JdbcTemplate jdbc;

	@Autowired
	ProjectDao daoProject;

	@Autowired
	ProjectRoleDao daoProjectRole;

	@Autowired
	DraftMetadataDao daoDraftMetadata;



	// ■プロジェクト自体のシンプルCURD機能：

	public ProjectIds createProject( UUID ownerAccountUUID ) {

		log.info( "プロジェクト.新規作成 [認証アカウント;{}]", ownerAccountUUID );

		// プロジェクトを作成。
		ProjectIds ids = this.daoProject.create();

		// プロジェクトの作成者を「所有者」として権限作成。
		this.daoProjectRole.createOwner( ids.uuid, ownerAccountUUID );

		return ids;
	}


	/**
	 * プロジェクト詳細画面用データ取得（認証なし）
	 * @param projectId プロジェクトID
	 * @return 参照可能な場合はプロジェクト詳細画面用データを返す。
	 */
	@Transactional(readOnly = true)
	public ProjectViewData getDetailViewData( String projectId ) {

		log.info( "プロジェクト[{}].データ参照 [認証なし]", projectId );

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );


		ProjectIds ids = project.toIds();
		MetadataJson metadata;
		AggregateJson aggregate;
		ListdataJson listdata;

		// ■認証情報なしの場合は「公開済」かつ「非公開でない」プロジェクトのみアクセス可能。
		if ( project.published() && !project.hidden() ) {

			log.debug( "★公開プロジェクトの参照" );

			metadata = MetadataJson.parse( project.metadataJson() );
			aggregate = AggregateJson.parse( project.aggregateJson() );
			listdata = ListdataJson.parse( project.listdataJson() );
		}
		// ■権限のないプロジェクトの場合は 401:UNAUTHORIZED を返す。
		else {
			throw new RestApiException( HttpStatus.UNAUTHORIZED ); // 権限がない : 401
		}



		// ■詳細画面用に表示データを詰めて返す。
		var projectViewData = new ProjectViewData( ids );
		projectViewData.setMetadata( metadata );
		projectViewData.setAggregate( aggregate );
		projectViewData.setListdata( listdata );
		projectViewData.setPublishedDate( project.firstPublishedString( DateTimeFormatter.ISO_DATE ) );
		projectViewData.setEditing( project.editing() );
		projectViewData.setHidden( project.hidden() );
		return projectViewData;
	}

	/**
	 * プロジェクト詳細画面用データ取得（認証あり）
	 * @param projectId プロジェクトID
	 * @param authAccountUUID ログイン認証したアカウントのUUID
	 * @return
	 */
	@Transactional(readOnly = true)
	public ProjectViewData getDetailViewData( String projectId, UUID authAccountUUID ) {

		log.info( "プロジェクト[{}].データ参照 [認証アカウント;{}]", projectId, authAccountUUID );

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );



		ProjectIds ids = project.toIds();
		MetadataJson metadata;
		AggregateJson aggregate;
		ListdataJson listdata;

		// ■公開実績あり
		if ( project.published() ) {
			// 公開実績のあるプロジェクトの場合、ElasticSearch API からデータを取得する。

			// ■■非公開プロジェクト
			if ( project.hidden() ) {
				// 非公開プロジェクトの場合は参照権限チェック。
				this.validateProjectRead( flags );

				log.debug( "★非公開プロジェクトの参照" );

				metadata = MetadataJson.parse( project.metadataJson() );
				aggregate = AggregateJson.parse( project.aggregateJson() );
				listdata = ListdataJson.parse( project.listdataJson() );
			}
			// ■■公開プロジェクト
			else {
				// 公開プロジェクトの場合は、特に権限不要で画面表示。
				log.debug( "★公開プロジェクトの参照" );

				metadata = MetadataJson.parse( project.metadataJson() );
				aggregate = AggregateJson.parse( project.aggregateJson() );
				listdata = ListdataJson.parse( project.listdataJson() );
			}
		}
		// ■公開実績なし
		else {
			// 未公開プロジェクト（新規作成：編集中）の場合は参照権限チェック。
			this.validateProjectRead( flags );

			log.debug( "★未公開プロジェクトの参照" );

			// まだ一度も公開されていないので、ESのデータは存在しない。
			metadata = null;
			aggregate = null;
			listdata = null;
		}



		// ■詳細画面用に表示データを詰めて返す。
		var projectViewData = new ProjectViewData( ids );
		projectViewData.setMetadata( metadata );
		projectViewData.setAggregate( aggregate );
		projectViewData.setListdata( listdata );
		projectViewData.setPublishedDate( project.firstPublishedString( DateTimeFormatter.ISO_DATE ) );
		projectViewData.setEditing( project.editing() );
		projectViewData.setHidden( project.hidden() );
		return projectViewData;
	}


	// 取得（プレビュー機能用の画面表示データ取得）
	public ProjectViewData getDetailPreviewData( String projectId, UUID authAccountUUID ) {

		// ★プレビュー機能の方ではDraftデータからProjectViewDataを持って来る。★

		log.info( "プロジェクト[{}].プレビュー参照 [認証アカウント;{}]", projectId, authAccountUUID );

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );


		// プレビューをするにはプロジェクトに対して最低でも参照権限を持っていなければならない。
		this.validateProjectRead( flags );

		var data = this.daoDraftMetadata.read( project.uuid() );
		var draftdata = new TDraftMetadataDataReader( data );

		ProjectIds ids = project.toIds();
		MetadataJson metadata = MetadataJson.parse( draftdata.metadataJson() );
		AggregateJson aggregate = AggregateJson.parse( draftdata.aggregateJson() );
		ListdataJson listdata = ListdataJson.parse( draftdata.listdataJson() );

		var projectViewData = new ProjectViewData( ids );
		// JSONデータ部はDraftデータから取ってくる。
		projectViewData.setMetadata( metadata );
		projectViewData.setAggregate( aggregate );
		projectViewData.setListdata( listdata );
		// 他のステータスはProject確定データから渡す。
		projectViewData.setPublishedDate( project.firstPublishedString( DateTimeFormatter.ISO_DATE ) );
		projectViewData.setEditing( project.editing() );
		projectViewData.setHidden( project.hidden() );
		return projectViewData;
	}



	private TProjectDataReader selectProjectData( final String projectId ) {

		var data = this.daoProject.read( projectId );

		if ( null == data ) throw new RestApiException( HttpStatus.NOT_FOUND ); // そもそも指定されたProjectIDが見付からない場合は404

		return new TProjectDataReader( data );
	}






	//TODO：この辺の処理も再利用性がありそうなので、プロジェクト権限DAOあたりに持っていく事を検討。

	private static class ProjectFlagDataReader extends MapDataReader {

		private ProjectFlagDataReader( Map<String, Object> data ) {
			super( data );
		}

		public boolean admin() {
			return nvl("admin");
		}
		public boolean owner() {
			return nvl("owner");
		}
		public boolean writable() {
			return nvl("writable");
		}
		public boolean readable() {
			return nvl("readable");
		}
	}

	private ProjectFlagDataReader selectProjectFlags( UUID projectUUID, UUID authAccountUUID ) {

		final String sql
				= "SELECT admin, owner, writable, readable"
				+ "  FROM t_user AS u"
				// t_user に t_account を紐付ける。（これは１対１で必ずある）
				+ " INNER JOIN t_account AS a"
				+ "    ON u.account_uuid = a.uuid"
				+ "   AND u.account_uuid = ?"
				// 該当プロジェクトに対する t_project_role を紐付ける。（これはあったりなかったり）
				+ "  LEFT join t_project_role AS r"
				+ "    ON r.account_uuid = a.uuid"
				+ "   AND r.project_uuid = ?";

		Object[] args = {
				authAccountUUID,
				projectUUID,
		};

		Map<String, Object> flags = SpringJdbcUtil.MapQuery.one( jdbc, sql, args );

		return new ProjectFlagDataReader( flags );
	}

	/**
	 * プロジェクト権限フラグの「参照権限」チェック
	 * @param flags
	 */
	private void validateProjectRead( ProjectFlagDataReader flags ) {
		// 管理者ならOK
		if ( flags.admin() ) return;

		// 所有者ならOK
		if ( flags.owner() ) return;

		// 何れかの権限があればOK（フラグの持ち方が冗長）
		if ( flags.writable()  || flags.readable() ) return;

		throw new RestApiException( HttpStatus.FORBIDDEN ); // 参照可能な権限がない場合は 403 
	}

	/**
	 * プロジェクト権限フラグの「更新権限」チェック
	 * @param flags
	 */
	private void validateProjectEdit( ProjectFlagDataReader flags ) {
		// 管理者ならOK
		if ( flags.admin() ) return;

		// 所有者ならOK
		if ( flags.owner() ) return;

		// 更新権限があればOK（フラグの持ち方が冗長）
		if ( flags.writable() ) return;

		throw new RestApiException( HttpStatus.BAD_REQUEST ); // 更新可能な権限がない場合は 400
	}




	// ■プロジェクト関係者関連機能：

	public ProjectMember inviteProjectEditor(
			final String projectId,
			final UUID inviteAccountUUID,
			final UUID authAccountUUID) {

		// ■Editor;共同編集者 は writable な権限で追加。
		log.info( "プロジェクト[{}].関係者[共同編集者;{}] の招待 [認証アカウント：{}]"
				, projectId
				, inviteAccountUUID
				, authAccountUUID
		);

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );

		// プロジェクトに対する更新権限を検証。
		this.validateProjectEdit( flags );



		// ■共同編集者（writable権限）の作成。
		final UUID projectUUID = project.uuid();
		final UUID accountUUID = inviteAccountUUID;
		this.daoProjectRole.createEditor( projectUUID, accountUUID, null ); // 編集者は無期限固定。

		// 作成した権限データを返す。
		ProjectMember editor = this.selectProjectMember( projectUUID, accountUUID );

		// FIXME：招待メールの送信まで完了してトランザクション閉じる。

		return editor;
	}
	@Deprecated //FXIME：【仕様凍結中】
	public ProjectMember inviteProjectObserver(
			final String projectId,
			final UUID inviteAccountUUID,
			final UUID authAccountUUID,
			final LocalDate expireDate ) {

		// ■Observer;共同閲覧者 は readable な権限で追加。
		log.info( "プロジェクト[{}].関係者[共同閲覧者;{}] の招待 [認証アカウント：{}]"
				, projectId
				, inviteAccountUUID
				, authAccountUUID
		);

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );

		// プロジェクトに対する更新権限を検証。
		this.validateProjectEdit( flags );



		// ■共同閲覧者（readable権限）の作成。
		final UUID projectUUID = project.uuid();
		final UUID accountUUID = inviteAccountUUID;
		this.daoProjectRole.createObserver( projectUUID, accountUUID, expireDate );
		// 作成した権限データを返す。
		ProjectMember observer = this.selectProjectMember( projectUUID, accountUUID );

		// FIXME：招待メールの送信まで完了してトランザクション閉じる。

		return observer;
	}

	private ProjectMember selectProjectMember( UUID projectUUID, UUID accountUUID ) {

		var data = this.daoProjectRole.read( accountUUID, projectUUID );

		if ( null == data ) throw new RestApiException( HttpStatus.NOT_FOUND );

		var reader = new MapDataReader( data );
		return toProjectMemberBean( reader );
	}


	public ProjectMemberList getProjectMemberList( String projectId, UUID authAccountUUID ) {

		log.info( "プロジェクト[{}].関係者一覧取得 [認証アカウント;{}]", projectId, authAccountUUID );

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );



		// 取り敢えず参照権限をチェック。
		this.validateProjectRead( flags );

		// プロジェクト関係者を取得し、画面用にデータを返す。
		return this.selectProjectMembers( project );
	}

	private ProjectMemberList selectProjectMembers( TProjectDataReader tProject ) {
		final String sql
				= "SELECT a.uid"
				+ "     , r.account_uuid"
				+ "     , r.project_uuid"
				+ "     , u.uuid AS user_uuid"
				//      権限フラグ系
				+ "     , r.owner"
				+ "     , r.writable"
				+ "     , r.readable"
				+ "     , u.admin"
				//      登録情報
				+ "     , r.enabled"
				+ "     , ( r.expire_date IS NOT NULL AND r.expire_date > current_date ) AS expired"
				+ "     , r.expire_date"
				+ "     , r.created_at"
				//      ユーザプロファイル
				+ "     , u.profile_json"
				+ "  FROM t_user AS u"
				+ " INNER JOIN t_account AS a"
				+ "    ON u.account_uuid = a.uuid"
				+ " INNER JOIN t_project_role AS r"
				+ "    ON r.account_uuid = a.uuid"
				+ "   AND r.project_uuid = ?"
				+ "   AND ( r.expire_date IS NULL OR r.expire_date > current_date )"
				// ソート
				+ " ORDER BY created_at DESC";
		Object[] args = { tProject.uuid() };


		// プロジェクト関係者（登録者・共同編集者・共同閲覧者）
		ProjectMember owner           = null;               // 登録者
		List<ProjectMember> editors   = new ArrayList<>();  // 共同編集者
		List<ProjectMember> observers = new ArrayList<>();  // 共同閲覧者

		var members = this.jdbc.queryForList( sql, args );
		var reader = new MapDataReader();
		for ( Map<String, Object> member : members ) {
			reader.data = member;

			ProjectMember entity = this.toProjectMemberBean( reader );

			// リレーションが所有格の場合は「登録者」のデータ
			if ( reader.bool( "owner" ) ) {
				owner = entity;
			}
			// リレーションが編集権限を持っている場合は「共同編集者」のデータ
			else if ( reader.bool( "writable" ) ) {
				editors.add( entity );
			}
			// リレーションが参照権限を持っている場合は「共同閲覧者」のデータ
			else if ( reader.bool( "readable" ) ) {
				// FIXME：【仕様凍結】DDBJアカウント統合の対応後に仕様決定。
				observers.add( entity );
			}
			// 何れのフラグも立っていないリレーションがあったら不正データ。
			else {
				// 有り得ないので警告ログだけ出してnop.
				log.warn( "権限フラグ設定の不正データ [project:{}({})／account:{}({})]"
						, tProject.id()
						, tProject.uuid()
						, entity.getUid()
						, entity.getAccountUUID()
				);
			}
		}

		return new ProjectMemberList(
				tProject.id(),
				tProject.uuid(),
				owner,
				editors,
				observers );
	}


	private ProjectMember toProjectMemberBean( MapDataReader reader ) {
		var entity = new ProjectMember();
		entity.setUid( reader.string( "uid" ) );
		entity.setAccountUUID( reader.uuid( "account_uuid" ) );
		entity.setUserUUID( reader.uuid( "user_uuid" ) );

		entity.setEnabled( reader.bool( "enabled" ) );
		entity.setExpired( reader.bool( "expired" ) );
		entity.setExpireDate( reader.date( "expire_date" ) );
		entity.setCreatedAt( reader.timestamp( "created_at" ) );

		entity.setProfile( reader.string( "profile_json" ) );
		return entity;
	}


	public int updateProjectMemberEnabled(
			final String projectId,
			final UUID memberAccountUUID,
			final UUID authAccountUUID,
			final boolean enabled ) {

		if ( enabled ) {
			log.info( "プロジェクト[{}].関係者[{}] の有効化 [認証アカウント：{}]"
					, projectId
					, memberAccountUUID
					, authAccountUUID
			);
		} else {
			log.info( "プロジェクト[{}].関係者[{}] の無効化 [認証アカウント：{}]"
					, projectId
					, memberAccountUUID
					, authAccountUUID
			);
		}

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );

		// 認証アカウントのプロジェクト更新権限をチェック。
		this.validateProjectEdit( flags );


		final UUID projectUUID = project.uuid();
		final UUID accountUUID = memberAccountUUID;
		return updateRoleEnabled( projectUUID, accountUUID, enabled );
	}

	private int updateRoleEnabled(
			final UUID projectUUID,
			final UUID accountUUID,
			final boolean enabled ) {

		// ■有効化するアカウントの権限データを取得。
		MapDataReader role = selectRole( projectUUID, accountUUID );

		// ★既に失効している権限の場合、有効無効のステータス変更不可。（取り敢えずバッドリクエスト）
		if ( role.bool( "expired" ) ) throw new RestApiException( HttpStatus.BAD_REQUEST );

		// ★執行していなくても、所有格ユーザの停止は現状では出来ない。（一応これもバッドリクエスト）
		if ( role.bool( "owner" ) && !enabled ) throw new RestApiException( HttpStatus.BAD_REQUEST );


		// 既に指定権限の場合は省略。
		if ( role.bool( "enabled" ) == enabled ) return -1;

		// DAOのメソッドで enabled を更新して終了。
		return this.daoProjectRole.updateProjectRoleEnabled( projectUUID, accountUUID, enabled );
	}
	private MapDataReader selectRole( UUID projectUUID, UUID accountUUID ) {

		final String sql
				= "SELECT owner"
				+ "     , enabled"
				+ "     , ( expire_date IS NOT NULL AND expire_date > current_date ) AS expired"
				+ "  FROM t_project_role"
				+ " WHERE project_uuid = ?"
				+ "   AND account_uuid = ?";
		Object[] args = {
				projectUUID,
				accountUUID,
		};

		var data = MapQuery.one( this.jdbc, sql, args );

		if ( null == data ) throw new RestApiException( HttpStatus.NOT_FOUND ); // そもそも指定された権限が見付からない場合は404

		return new MapDataReader( data );
	}

	public int deleteProjectMember(
			final String projectId,
			final UUID memberAccountUUID,
			final UUID authAccountUUID ) {

		log.info( "プロジェクト[{}].関係者[{}] の削除 [認証アカウント：{}]"
				, projectId
				, memberAccountUUID
				, authAccountUUID
		);

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );

		// 認証アカウントのプロジェクト更新権限をチェック。
		this.validateProjectEdit( flags );


		// ■削除するアカウントの権限データを取得。
		final UUID projectUUID = project.uuid();
		final UUID accountUUID = memberAccountUUID;
		return this.deleteRole( projectUUID, accountUUID );
	}
	private int deleteRole(
			final UUID projectUUID,
			final UUID accountUUID ) {

		// ■削除するアカウントの権限データを取得。
		MapDataReader role = selectRole( projectUUID, accountUUID );


		// ★所有格のユーザ権限は削除できないし、そもそも失効もさせない。（取り敢えずバッドリクエスト）
		if ( role.bool( "owner" ) ) throw new RestApiException( HttpStatus.BAD_REQUEST );

		// 失効または無効化されたユーザ権限なら削除する。
		final boolean expired = role.bool( "expired" );
		final boolean disabled = !role.bool( "enabled" );
		if ( expired || disabled ) {
			return this.daoProjectRole.deleteProjectRoleEnabled( projectUUID, accountUUID );
		}
		// ★失効も無効化もされていない権限削除はバッドリクエスト。
		else {
			throw new RestApiException( HttpStatus.BAD_REQUEST );
		}
	}



	// ■Draftデータ関連処理

	// 作成
	public ProjectIds createDraftData(
			final String projectId,
			final UUID authAccountUUID ) {

		log.info( "プロジェクト[{}].編集開始 [認証アカウント：{}]"
				, projectId
				, authAccountUUID
		);

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );

		// 認証アカウントのプロジェクト更新権限をチェック。
		this.validateProjectEdit( flags );



		// ■Draftデータを作成する。
		// ・既にDraftデータが存在する場合はNG
		// ・Draftデータ作成時に、あわせて更新トークンを生成しておく。（※）
		if ( this.daoDraftMetadata.exists( project.uuid() ) ) {
			// 単一のプロジェクトから複数の編集モードは実行できない。
			throw new RestApiException( HttpStatus.BAD_REQUEST );
		}
		final String sql
				= "INSERT INTO t_draft_metadata"
				+ "( project_uuid"
				+ ", metadata_json"
				+ ", aggregate_json"
				+ ", data_token"
				+ ")"
				+ "SELECT uuid AS project_uuid"
				+ "     , metadata_json"
				+ "     , aggregate_json"
				+ "     , encode( digest(aggregate_json, 'md5'), 'hex') AS data_token"
				//        ※ 更新トークン ※
				// 初期状態だと json データは null なのでそのまま null を移行する。
				// null をベースに生成したトークンも null なので、初期状態ではトークンは null になる。
				// aggregate_json にタイムスタンプも仕込んでおく事で、データ由来且つタイミング依存のトークンに出来る。
				+ "  FROM t_project"
				+ " WHERE uuid = ?";

		Object[] args = { project.uuid() };

		this.jdbc.update( sql, args );

		// ドラフトデータを作成したら t_project.editing フラグを立てる。
		this.daoProject.updateEditingFlag( project.uuid(), true );


		// FIXME：【NextCloud】既にファイルがある場合は編集用コピーを作る処理が必要。（後処理）


		// ドラフトデータを作成したら ProjectIds を返す。（他に特に返すものがない）
		return new ProjectIds( project.uuid(), project.id() );
	}

	// 削除（編集破棄）
	public ProjectIds deleteDraftData(
			final String projectId,
			final UUID authAccountUUID ) {

		log.info( "プロジェクト[{}].編集破棄 [認証アカウント：{}]"
				, projectId
				, authAccountUUID
		);

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );

		// 認証アカウントのプロジェクト更新権限をチェック。
		this.validateProjectEdit( flags );


		// ■本処理：Draftデータを削除する。
		// ・クローズされていない申請データが残っている場合はNG
		if ( this.existsActiveRequest( project.uuid() ) ) {
			// 出した申請が残ってるうちは削除させない。（差し戻しとかが有り得るので）
			throw new RestApiException( HttpStatus.BAD_REQUEST );
		}
		// 残ってる申請データが無いなら消して良い。
		this.daoDraftMetadata.delete( project.uuid() );


		// ■後処理：ドラフトデータを削除したら t_project.editing フラグも落とす。
		this.daoProject.updateEditingFlag( project.uuid(), false );

		// ■後処理：NextCloudのDraftファイルをフォルダごと削除する。
		this.fileService.discard( authAccountUUID, projectId );



		// ドラフトデータを削除したら ProjectIds を返す。（他に特に返すものがない）
		return new ProjectIds( project.uuid(), project.id() );
	}

	//FIXME：これは普通にPublishDaoに持っていくべき。
	private boolean existsActiveRequest( UUID projectUUID ) {

		// クローズされていない申請があるか。
		final String sql
				= "SELECT * FROM t_publish"
				+ " WHERE project_uuid = ?"
				+ "   AND closed = false";
		Object[] args = { projectUUID };

		return MapQuery.exists( jdbc, sql, args );
	}

	// 取得（メタデータの取得）
	public DraftMetadata getDraftMetadata(
			final String projectId,
			final UUID authAccountUUID ) {

		log.info( "プロジェクト[{}].編集データ取得 [認証アカウント;{}]"
				, projectId
				, authAccountUUID
		);

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );

		// プロジェクトの参照権限をチェック。
		this.validateProjectRead( flags );


		// ■Draftメタデータを取得。
		var draft = this.selectDraftMetadata( project.uuid() );

		// まだメタデータが登録されていなければ not found エラーにする。
		// FIXME：エラーにするのは良いけど 404 だとレコード自体が無いの特別が付かないので要検討。
		if ( null == draft.metadataJson() ) throw new RestApiException( HttpStatus.NOT_FOUND );

		// 取得した t_draft_metadata からメタデータ部とデータ更新トークンを返す。
		final ExcelSheetData[] metadata = MetadataJson.parse( draft.metadataJson() ).sheets;
		final String token = draft.dataToken();
		return new DraftMetadata( metadata, token );
	}

	// 更新（更新トークンを利用した排他制御付き）

	public DraftMetadata updateDraftMetadata(
			final String projectId,
			final UUID authAccountUUID,
			final DraftMetadata posted ) {

		log.info( "プロジェクト[{}].編集データ更新 [認証アカウント;{}]"
				, projectId
				, authAccountUUID
		);

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );

		// 認証アカウントのプロジェクト更新権限をチェック。
		this.validateProjectEdit( flags );


		// ■更新トークンチェック
		this.validateUpdateData( projectId, project.uuid(), posted );

		// ■メタデータの集計、更新、トークン再発行。
		// ・POSTされたメタデータ（metadata_json）を基に、集計データ（aggregate_json）を生成する。
		// ・Draftメタデータを更新する
		// ・更新時にあわせて集計データを基にデータ更新トークンを再設定（SQL）する。
		// ・再設定されたデータ更新トークンを付けてDraftメタデータを返す。
		MetadataJson metadata = new MetadataJson( posted.metadata );
		MetadataSummarizer summarizer = new MetadataSummarizer( projectId, posted.metadata );
		AggregateJson aggregate = summarizer.sumAggregateJson();
		ListdataJson listdata   = summarizer.extractListdataJson();
		final String token = this.updateDraftMetadata(
				project.uuid(),
				metadata,
				aggregate,
				listdata );

		// 無事更新されたら新しい更新トークンを使ってDraftMetadataを返す。
		return new DraftMetadata( posted.metadata, token );
	}


	private TDraftMetadataDataReader selectDraftMetadata( UUID projectUUID ) {

		var data = this.daoDraftMetadata.read( projectUUID );

		if ( null == data ) throw new RestApiException( HttpStatus.NOT_FOUND );

		return new TDraftMetadataDataReader( data );
	}

	private void validateUpdateData(
			final String projectId,
			final UUID projectUUID,
			final DraftMetadata posted ) {

		// 現在のDBのデータを取得。
		TDraftMetadataDataReader stored = selectDraftMetadata( projectUUID );

		// ■ProjectIdチェック
		if ( !compareProjectId( projectId, posted ) ) {
			// ProjectIdが一致しなかった場合はエラー
			throw new RestApiException( HttpStatus.BAD_REQUEST );
		}

		// ■更新トークンチェック
		if ( !updatableDataToken( projectId, stored, posted ) ) {
			// トークンが一致しなかった場合はエラー（排他エラーなので CONFLICT;競合 にしておく）
			throw new RestApiException( HttpStatus.CONFLICT );
		}
	}

	private boolean compareProjectId(
			final String projectId,
			final DraftMetadata posted ) {

		MetadataAccessor accessor = MetadataAccessor.compile( posted.metadata );
		var xsProject = accessor.sheet( "project" );

		// ■そもそもProjectIdが一意に入力されていなかったらNG
		if ( 1 != xsProject.size() ) return false;

		var row = xsProject.row( 0 );

		final String id = DataRowReader.string( row, "id" );

		log.info( "{} posted metadata.project.id[{}]", projectId, id );

		// ■入力されたIDとProjectIdが一致する場合はOK、一致しない場合はNG。
		return projectId.equals( id );
	}

	private boolean updatableDataToken(
			final String projectId,
			final TDraftMetadataDataReader stored,
			final DraftMetadata posted ) {

		// まだDB格納データに更新トークンが付与されていない場合は無条件で更新可能。
		if ( null == stored.dataToken() ) return true;

		final String token = stored.dataToken();

		log.info( "{} db-stored token[{}] posted token[{}]", token, posted.token );

		// 更新トークンが付与されている場合は、トークンが一致する場合だけ更新可能。
		if ( token.equals( posted.token ) ) return true;

		// 更新不可。
		return false;
	}


	private String updateDraftMetadata(
			final UUID projectUUID,
			final MetadataJson metadata,
			final AggregateJson aggregate,
			final ListdataJson listdata ) {

		//FIXME：これって普通にDraftMetadataDAOに持っていくべきだったのでは。（何でここに置いたんだっけ・・・後で修正）

		final String metadataJson = metadata.stringify();
		final String aggregateJson = aggregate.stringify();
		final String listdataJson = listdata.stringify();

		final String sql
				= "UPDATE t_draft_metadata"
				+ "   SET metadata_json = ?"
				+ "     , aggregate_json = ?"
				+ "     , listdata_json = ?"
				+ "     , data_token = encode( digest( ?, 'md5' ), 'hex' )"
				+ "     , updated_at = current_timestamp"
				+ " WHERE project_uuid = ?"
				+ " RETURNING data_token";

		Object[] args = {
				metadataJson,
				aggregateJson,
				listdataJson,
				aggregateJson, // encode( digest() ) 用の２つ目。
				projectUUID,
		};

		// UPDATE文で更新されたトークンを返す。
		Map<String, Object> returned = this.jdbc.queryForMap( sql, args );
		var reader = new MapDataReader( returned );
		return reader.string( "data_token" );
	}




	// 申請処理

	public long requestPublishProject(
			final String projectId,
			final UUID authAccountUUID ) {

		log.info( "プロジェクト[{}].申請処理 [認証アカウント;{}]"
				, projectId
				, authAccountUUID
		);

		// プロジェクトデータを取得。
		var project = this.selectProjectData( projectId );

		// プロジェクト権限を取得。
		var flags = this.selectProjectFlags( project.uuid(), authAccountUUID );

		// 認証アカウントのプロジェクト更新権限をチェック。
		this.validateProjectEdit( flags );


		// ■多重申請の防止
		if ( this.existsActiveRequest( project.uuid() ) ) {
			// 出した申請が残ってるうちは追加申請させない。
			throw new RestApiException( HttpStatus.CONFLICT );
		}

		// ■申請処理
		return this.doPublishRequest( project.uuid(), authAccountUUID );
	}
	private long doPublishRequest( UUID projectUUID, UUID accountUUID ) {

		final String sql
				= "INSERT INTO t_publish"
				+ "( account_uuid"
				+ ", project_uuid"
				+ ", metadata_json"
				+ ", aggregate_json"
				+ ", listdata_json"
				+ ", status"
				+ ")"
				+ "SELECT a.uuid AS account_uuid"
				+ "     , d.project_uuid"
				+ "     , d.metadata_json"
				+ "     , d.aggregate_json"
				+ "     , d.listdata_json"
				+ "     , '申請中' AS status"
				+ "  FROM t_draft_metadata as d, t_account as a"
				+ " WHERE d.project_uuid = ?"
				+ "   AND a.uuid = ?"
				+ " RETURNING num";
		Object[] args = {
				projectUUID,
				accountUUID,
		};


		// 発行した申請番号（シーケンス値）を返す。
		Map<String, Object> returned = MapQuery.one( this.jdbc, sql, args );
		var reader = new MapDataReader( returned );
		return reader.integer( "num" );
	}

	// ユーザ操作として行えるプロジェクト操作としては、申請まで。
	// この後は管理者（t_user.admin:true）による[差し戻し]または[承認]で申請がクローズされる。
	// （[取り下げ]は今の所仕様には無いが、将来的には出て来る筈）
	// ＞ PublishService
}
