package ddbjld.api.app.service.dao;

import static ddbjld.api.common.utility.SpringJdbcUtil.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.data.values.ProjectIds;

/**
 * プロジェクトテーブルのDAOクラス.
 *
 * @author m.tsumura
 *
 **/
@Repository
@Slf4j
public class ProjectDao {
	
	public static class TProjectDataReader extends MapDataReader {
		
		// 疑似Entityクラス
		
		public TProjectDataReader() {}
		public TProjectDataReader( Map<String, Object> data ) {
			super( data );
		}
		public TProjectDataReader bind( Map<String, Object> data ) {
			this.data = data;
			return this;
		}
		
		public UUID uuid() {
			return super.uuid( "uuid" );
		}
		
		public String id() {
			return super.string( "id" );
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
		
		
		public boolean published() {
			return null != column( super.data, "first_published_at" );
		}
		public LocalDateTime firstPublishedAt() {
			return super.timestamp( "first_published_at" );
		}
		public String firstPublishedString( DateTimeFormatter formatter ) {
			LocalDateTime at = this.firstPublishedAt();
			return null == at ? null : at.format( formatter );
		}
		
		public boolean editing() {
			return super.bool( "editing" );
		}
		
		public boolean hidden() {
			return null != column( super.data, "hidden_at" );
		}
		public LocalDateTime hiddenAt() {
			return super.timestamp( "hidden_at" );
		}
		
		public LocalDateTime createdAt() {
			return super.timestamp( "created_at" );
		}
		public LocalDateTime updatedAt() {
			return super.timestamp( "updated_at" );
		}
		
		
		public ProjectIds toIds() {
			return new ProjectIds( this.uuid(), this.id() );
		}
	}
	
    @Autowired @Qualifier("jvarJdbc")
    private JdbcTemplate jdbc;
    
    public ProjectIds create() {
    	
    	final String sql 
    			= "INSERT INTO t_project"
    			+ "( uuid, id )"
    			+ "VALUES"
    			+ "( gen_random_uuid(), 'RMM' || to_char( nextval('project_id_seq'), 'FM999909999' ) )"
    			+ "RETURNING uuid, id";
    	
    	Map<String, Object> returned = this.jdbc.queryForMap( sql );
		return asProjectIds( returned );
    }
    

	public List<String> eachProjectId() {
		final String sql = "SELECT id FROM t_project";
		return this.jdbc.queryForList( sql, String.class );
	}
    
	
	public ProjectIds ids( String projectId ) {
		
		final String sql 
				= "SELECT uuid, id"
				+ "  FROM t_project"
				+ " WHERE id = ?";
		Object[] args = { projectId };
		
		
		Map<String, Object> data = MapQuery.one( jdbc, sql, args );
		return asProjectIds( data );
	}
	
	public ProjectIds ids( UUID projectUUID ) {
		
		final String sql 
				= "SELECT uuid, id"
				+ "  FROM t_project"
				+ " WHERE uuid = ?";
		Object[] args = { projectUUID };


		Map<String, Object> data = MapQuery.one( jdbc, sql, args );
		return asProjectIds( data );
	}
	
	private static ProjectIds asProjectIds( Map<String, Object> data) {
		
		if ( null == data ) return null;
		
		var reader = new TProjectDataReader( data );
		
		return new ProjectIds(
				reader.uuid(),
				reader.id() );
	}
	
	public Map<String, Object> read( String projectId ) {
		
		final String sql = "SELECT * FROM t_project WHERE id = ?";
		Object[] args = { projectId };

		return MapQuery.one( jdbc, sql, args );
	}

	
	public int updateEditingFlag( UUID uuid, boolean flag ) {
		
		final String sql = "UPDATE t_project"
				+ " SET editing = ?"
				+ "   , updated_at = current_timestamp"
				+ " WHERE uuid = ?";
		
		Object[] args = { 
				flag,
				uuid,
		};

		return this.jdbc.update( sql, args );
	}
	
	public int updateFirstPublishedAt( UUID uuid ) {
		final String sql = "UPDATE t_project"
				+ " SET first_published_at = current_timestamp"
				+ "   , updated_at = current_timestamp"
				+ " WHERE uuid = ?"
				+ "   AND first_published_at IS NULL";
		
		Object[] args = {
				uuid,
		};

		return this.jdbc.update( sql, args );
		
	}
}
