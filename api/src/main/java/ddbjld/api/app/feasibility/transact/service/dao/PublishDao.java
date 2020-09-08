package ddbjld.api.app.feasibility.transact.service.dao;

import static ddbjld.api.common.utility.SpringJdbcUtil.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 申請依頼テーブルのDAOクラス.
 *
 * @author m.tsumura
 *
 **/
@Repository
@Slf4j
public class PublishDao {
	
	public static class TPublishDataReader extends MapDataReader {

		// 疑似Entityクラス
		
		public TPublishDataReader() {}
		public TPublishDataReader( Map<String, Object> data ) {
			super( data );
		}
		public TPublishDataReader bind( Map<String, Object> data ) {
			super.data = data;
			return this;
		}
		
		public int num() {
			return super.integer( "num" );
		}
		
		public UUID accountUuid() {
			return super.uuid( "account_uuid" );
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
		
		public LocalDateTime requestedAt() {
			return super.timestamp( "requested_at" );
		}
		public String status() {
			return super.string( "status" );
		}
		public boolean closed() {
			return super.bool( "closed" );
		}
		
		public LocalDateTime updatedAt() {
			return super.timestamp( "updated_at" );
		}
	}
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> read( final long num ) {
		final String sql = "SELECT * FROM t_publish WHERE num = ?";
		Object[] args = {
				num,
		};
		return MapQuery.one( jdbcTemplate, sql, args );
	}
	
	
	
	// FIXME：【承認フロー】
	// 当分の間は手運用で回す事になっているので、申請・承認のワークフロー関連機能は後々拡張される見込み。
	// 一般的には「申請／取り下げ／差し戻し／承認」で、申請ステータスの有限状態オートマトンを実装する必要がある。
	// 今は自主的な「取り下げ」が機能要件として存在せず、「差し戻し＝否認；NG」か「承認；OK」の２択しかない。
	
	public int permit( final long num ) {
		return this.close( num, "承認" );
	}
	public int deny( final long num ) {
		// TODO：事由をコメントとして残しておけるようにした方が良いかも。
		return this.close( num, "差し戻し" );
	}
	private int close( final long num, String status ) {
		final String sql 
				= "UPDATE t_publish"
				+ "   SET closed = true"
				+ "     , status = ?"
				+ "     , updated_at = current_timestamp"
				+ " WHERE num = ?";
		Object[] args = {
				status,
				num,
		};
		
		return this.jdbcTemplate.update( sql, args );
	}
}
