package ddbjld.api.app.service.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.common.utility.SpringJdbcUtil.MapDataReader;

@Repository
@Slf4j
public class FacetItemsDao {

	public static class TFacetItemsDataReader extends MapDataReader {

		public TFacetItemsDataReader() {}
		public TFacetItemsDataReader( Map<String, Object> data ) {
			super( data );
		}
		public TFacetItemsDataReader bind( Map<String, Object> data ) {
			super.data = data;
			return this;
		}
		
		public String category() {
			return super.string( "category" );
		}
		public String item() {
			return super.string( "item" );
		}
	}
	
    @Autowired @Qualifier("jvarJdbc")
    private JdbcTemplate jdbc;
	
    public List<Map<String, Object>> all(){
    	final String sql = "SELECT * FROM t_facet_items";
    	return this.jdbc.queryForList( sql );
    }

    public void refresh() {
		
    	// 一旦まず既存のデータ全消し。
    	// ※ ポスグレならトランザクション処理でもTRUNCATEが使えて且つロールバックが効く
		final String delete = "DELETE FROM t_facet_items";
		this.jdbc.update( delete );
		
		final String insert = 
				// FacetItemsProject からプロジェクトの区別なしで
				// [category, item] をGroupByで畳み込んで取り込む。
				"INSERT INTO t_facet_items"
				+ "   SELECT category, item"
				+ "     FROM t_facet_items_project"
				+ " GROUP BY category, item";
		this.jdbc.update( insert );
	}
}
