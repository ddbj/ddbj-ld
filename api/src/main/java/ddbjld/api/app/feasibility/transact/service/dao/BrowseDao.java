package ddbjld.api.app.feasibility.transact.service.dao;

import static ddbjld.api.common.utility.SpringJdbcUtil.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 閲覧テーブルのDAOクラス.
 *
 * @author m.tsumura
 *
 **/
@Repository
@Slf4j
public class BrowseDao {
	
	public static class TBrowsDataReader extends MapDataReader {

		// 疑似Entityクラス
		
		public TBrowsDataReader() {	}
		public TBrowsDataReader( Map<String, Object> data ) {
			super( data );
		}
		public TBrowsDataReader bind( Map<String, Object> data ) {
			super.data = data;
			return this;
		}
		
		public UUID uuid() {
			return super.uuid( "uuid" );
		}
		public UUID projectUuid() {
			return super.uuid( "project_uuid" );
		}
		
		public String label() {
			return super.string( "label" );
		}
		
		public LocalDate expireDate() {
			return super.date( "expire_date" );
		}
	}
	
    @Autowired @Qualifier("jvarJdbc")
    private JdbcTemplate jdbcTemplate;
    
    
}
