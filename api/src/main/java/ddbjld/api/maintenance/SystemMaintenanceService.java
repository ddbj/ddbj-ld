package ddbjld.api.maintenance;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.core.module.ElasticSearchModule;
import ddbjld.api.app.core.module.ElasticSearchModule.MetadataExtract;
import ddbjld.api.app.core.module.ElasticSearchModule.ProjectIndexType;
import ddbjld.api.app.service.dao.FacetItemsDao;
import ddbjld.api.app.service.dao.FacetItemsProjectDao;
import ddbjld.api.app.service.dao.ProjectDao;
import ddbjld.api.app.service.dao.ProjectRoleDao;
import ddbjld.api.app.service.dao.ProjectDao.TProjectDataReader;
import ddbjld.api.common.utility.SpringJdbcUtil.MapQuery;
import ddbjld.api.common.utility.data.MetadataSummarizer;
import ddbjld.api.data.json.AggregateJson;
import ddbjld.api.data.json.ExcelSheetData;
import ddbjld.api.data.json.ListdataJson;
import ddbjld.api.data.json.MetadataJson;
import ddbjld.api.data.values.ProjectIds;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class SystemMaintenanceService {

	@Autowired
	ElasticSearchModule elasticsearch;

	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	ProjectDao daoProject;
	
	@Autowired
	ProjectRoleDao daoProjectRole;
	
	
	@Autowired
	FacetItemsDao daoFacetItems;
	
	@Autowired
	FacetItemsProjectDao daoFacetItemsProject;
	

	public ProjectIds putProjectData( UUID ownerAccountUUID, String projectId, ExcelSheetData[] sheets ) {
		
		// ■登録データを準備
		final MetadataJson metadata = new MetadataJson( sheets );
		final MetadataSummarizer summarizer = new MetadataSummarizer( projectId, metadata.sheets );
		final AggregateJson aggregate = summarizer.sumAggregateJson();
		final ListdataJson listdata   = summarizer.extractListdataJson();
		
		// ■まずポスグレDBにINSERT
		final String sql = "INSERT INTO t_project"
				+ "( uuid, id, metadata_json, aggregate_json, listdata_json, first_published_at )"
				+ "VALUES"
				+ "( gen_random_uuid(), ?, ?, ?, ?, current_timestamp )"
				+ "RETURNING uuid, first_published_at";
		
		Object[] args = {
				projectId,
				metadata.stringify(),
				aggregate.stringify(),
				listdata.stringify(),
		};
		
		// プロジェクトデータを登録
		var data = MapQuery.one( jdbc, sql, args );
		var reader = new TProjectDataReader( data );
		final UUID projectUUID = reader.uuid();
		final LocalDateTime firstPublishedAt = reader.firstPublishedAt();
		final ProjectIds ids = new ProjectIds( projectUUID, projectId );
		
		// 所有格権限を登録
		this.daoProjectRole.createOwner( ids.uuid, ownerAccountUUID );
		
		
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
		log.info( "put public-project index: {}", result );
		
		// 登録・公開したプロジェクトの Ids{UUID/ID} を返す。
		return ids;
	}
	
	public void refreshFacetItems() {

		// ■DBに登録されているProjectを全列挙してFacetItemsProjectを更新。
		var project = new ProjectDao.TProjectDataReader();
		for ( String id : this.daoProject.eachProjectId() ) {
			
			project.bind( this.daoProject.read( id ) );
			final String metadataJson = project.metadataJson();
			final MetadataJson metadata = MetadataJson.parse( metadataJson );
			
			// ★メタデータを持ってない場合は消すだけ。
			if ( null == metadata ) {
				
				log.info( "★メタデータなし Project[{}]({})"
						, project.uuid()
						, project.id() );
				this.daoFacetItemsProject.delete( project.uuid() );
			}
			// ★非公開化プロジェクトの場合は消すだけ。
			else if (project.hidden()) {

				log.info( "★非公開化プロジェクト Project[{}]({})"
						, project.uuid()
						, project.id() );
				this.daoFacetItemsProject.delete( project.uuid() );
			}
			// ★メタデータを持っている公開プロジェクトの場合は、データを入れ直し。
			else {

				log.info( "★公開プロジェクトの検索データ更新 Project[{}]({})"
						, project.uuid()
						, project.id() );
				
				var extract = ElasticSearchModule.MetadataExtract.execute( metadata );
				
				this.daoFacetItemsProject.delete( project.uuid() );
				this.daoFacetItemsProject.bulkinsert( project.uuid()
						, extract.species()
						, extract.devices() );
			}
		}
		
		// ■最後に一括でFacetItemsをリフレッシュ。（DELETEしてからSELECT-INSERT）
		this.daoFacetItems.refresh();
	}
}
