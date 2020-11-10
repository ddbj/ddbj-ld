package ddbjld.api.app.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.core.module.ElasticSearchModule;
import ddbjld.api.app.core.module.ElasticSearchModule.MetadataExtract;
import ddbjld.api.app.core.module.ElasticSearchModule.ProjectIndexType;
import ddbjld.api.app.service.dao.DraftMetadataDao;
import ddbjld.api.app.feasibility.transact.service.dao.PublishDao;
import ddbjld.api.app.feasibility.transact.service.dao.PublishDao.TPublishDataReader;
import ddbjld.api.app.service.dao.FacetItemsDao;
import ddbjld.api.app.service.dao.FacetItemsProjectDao;
import ddbjld.api.app.service.dao.ProjectDao;
import ddbjld.api.app.service.dao.ProjectDao.TProjectDataReader;
import ddbjld.api.app.service.dao.UserDao;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.SpringJdbcUtil.MapQuery;
import ddbjld.api.data.json.AggregateJson;
import ddbjld.api.data.json.ListdataJson;
import ddbjld.api.data.json.MetadataJson;
import ddbjld.api.data.values.ProjectIds;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class PublishService {

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
	UserDao daoUser;
	
	@Autowired
	ProjectDao daoProject;
	
	@Autowired
	DraftMetadataDao daoDraftMetadata;
	
	@Autowired
	PublishDao daoPublish;
	
	@Autowired
	FacetItemsDao daoFacetItems;
	
	@Autowired
	FacetItemsProjectDao daoFacetItemsProject;
	

	// 承認
	public UUID permitPublishProject(
			final long num,
			final UUID authAccountUUID ) {
		
		log.info( "申請番号[{}].承認処理 [認証アカウント;{}]"
				, num
				, authAccountUUID
		);
		
		
		// 管理者（t_user.admin:true）であることを確認。
		this.daoUser.validateAdminableUser( authAccountUUID );
	
		
		// ■申請テーブルから確定データにJSONデータを移し、editingフラグを落とす。
		var projectIds = this.migrateJsonData( num );
		
		// 初公開の場合はリリース日（初回公開日時）を設定する。
		this.daoProject.updateFirstPublishedAt( projectIds.uuid );
		
		
		// ■ElasticSerarchに確定データをエクスポート。
		// ※ PUT方式なのでリランしても問題なく、NextCloud側の方がエラーのリセットがし難いのでこっちを先に反映しとく。
		var data = this.daoProject.read( projectIds.id );
		var project = new TProjectDataReader( data );
		
		
		//TODO：【追加改修】ESに検索データを送るのと併せて、Facet検索用のプルダウンデータソースもポスグレに持っておく必要がある。
		
		
		// Projectデータを送ってElasticSearchに検索データをエクスポートする。
		this.publishProjectIndex( project );
		
		
		
		// ■NextCloudのdraftフォルダのファイルを反映。
		this.fileService.publish( authAccountUUID, projectIds.id );
		
		
		
		// ■申請データをクローズして、ドラフトデータを削除する。
		if ( 1 != this.daoPublish.permit( num ) ) {
			// 念の為、更新件数がゼロ（１件以外）だったらエラーにしておく。
			throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
		}	
		this.daoDraftMetadata.delete( projectIds.uuid );

		
		
		// 特に返す情報が無いので、申請に紐付いている ProjectのPK:UUID を返しておく。
		return projectIds.uuid;
	}
	private ProjectIds migrateJsonData( final long num ) {
		
		// draftコピーのpublishデータをproject本体に反映。
		final String sql 
				= "UPDATE t_project"
				+ " SET metadata_json  = pub.metadata_json"
				+ "   , aggregate_json = pub.aggregate_json"
				+ "   , listdata_json  = pub.listdata_json"
				+ "   , editing = false"
				+ "   , updated_at = current_timestamp"
				+ " FROM t_publish AS pub"
				+ " WHERE pub.project_uuid = t_project.uuid"
				+ "   AND pub.num = ?"
				+ " RETURNING uuid, id";
		Object[] args = {
			num,
		};
		
		var data = MapQuery.one( jdbc, sql, args );
		if ( null == data ) throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
		return new TProjectDataReader( data ).toIds();
	}
	// プロジェクトデータの検索公開処理
	private void publishProjectIndex( TProjectDataReader project ) {

		ProjectIds ids = project.toIds();
		MetadataJson metadata = MetadataJson.parse( project.metadataJson() );
		AggregateJson aggregate = AggregateJson.parse( project.aggregateJson() );
		ListdataJson listdata = ListdataJson.parse( project.listdataJson() );
		LocalDateTime firstPublishedAt = project.firstPublishedAt();
		
		
		// ■検索用データ抽出
		var extract = MetadataExtract.execute( metadata );
		
		
		// ■検索用FacetItemsを更新
		this.daoFacetItemsProject.delete( ids.uuid );
		this.daoFacetItemsProject.bulkinsert( ids.uuid
				, extract.species()
				, extract.devices() );
		
		
		// ■検索用インデックスにデータ公開
		// ※ REST API 処理なので年のため最後に実行
		String result = this.elasticsearch.putProject( 
				ids, 
				extract, 
				aggregate, 
				listdata, 
				firstPublishedAt, 
				ProjectIndexType.PUBLIC );
		log.debug( "put public-project index: {}", result );
	}
	
	
	// 否認
	public UUID denyPublishProject(
			final long num,
			final UUID authAccountUUID ) {
		
		log.info( "申請番号[{}].否認処理 [認証アカウント;{}]"
				, num
				, authAccountUUID
		);
		
		
		// 管理者（t_user.admin:true）であることを確認。
		this.daoUser.validateAdminableUser( authAccountUUID );
		
		// ■申請データをクローズする。
		// ※ 否認の場合は、否認理由を修正して再申請が有り得るので、Draftの関連データやファイルはそのままにしておく。
		if( 1 != this.daoPublish.deny( num ) ) {
			// 念の為、更新件数がゼロ（１件以外）だったらエラーにしておく。
			throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		

		// 特に返す情報が無いので、申請に紐付いている ProjectのPK:UUID を返しておく。
		var data = this.daoPublish.read( num );
		return new TPublishDataReader( data ).projectUuid();
	}
}
