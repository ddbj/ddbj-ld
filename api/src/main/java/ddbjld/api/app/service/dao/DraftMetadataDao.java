package ddbjld.api.app.service.dao;

import static ddbjld.api.common.utility.SpringJdbcUtil.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * メタデータ編集テーブルのDAOクラス.
 *
 * @author m.tsumura
 *
 **/
@Repository
@Slf4j
public class DraftMetadataDao {
	
	public static class TDraftMetadataDataReader extends MapDataReader {

		// 疑似Entityクラス
		
		public TDraftMetadataDataReader() {}
		public TDraftMetadataDataReader( Map<String, Object> data ) {
			super( data );
		}
		public TDraftMetadataDataReader bind( Map<String, Object> data ) {
			super.data = data;
			return this;
		}
		
		public UUID projectUuid() {
			return super.uuid( "project_uuid" );
		}
		
		public String metadataJson() {
			return super.string( "metadata_json" );
		}
		public String aggregateJson() {
			return super.string( "aggregate_json" );
		}
		public String listdataJson() {
			return super.string( "listdata_json" );
		}
		
		public String dataToken() {
			return super.string( "data_token" );
		}
		
		public LocalDateTime createdAt() {
			return super.timestamp( "created_at" );
		}
		public LocalDateTime updatedAt() {
			return super.timestamp( "updated_at" );
		}
	}
	
    @Autowired @Qualifier("jvarJdbc")
    private JdbcTemplate jdbcTemplate;
    
	public Map<String, Object> read( final UUID projectUUID ) {
		final String sql = "SELECT * FROM t_draft_metadata WHERE project_uuid = ?";
		Object[] args = { projectUUID };
		return MapQuery.one( jdbcTemplate, sql, args );
	}
	
	public boolean exists( final UUID projectUUID ) {
		final String sql = "SELECT project_uuid FROM t_draft_metadata WHERE project_uuid = ?";
		Object[] args = { projectUUID };
		return MapQuery.exists( jdbcTemplate, sql, args );
	}
	
	public int delete( final UUID projectUUID ) {
		final String sql = "DELETE FROM t_draft_metadata WHERE project_uuid = ?";
		Object[] args = { projectUUID };
		return jdbcTemplate.update( sql, args );
	}
}
