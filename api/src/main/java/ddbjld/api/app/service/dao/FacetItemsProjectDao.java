package ddbjld.api.app.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.common.constants.SystemCode;

@Repository
@Slf4j
public class FacetItemsProjectDao {

    @Autowired
    private JdbcTemplate jdbc;

    
    public void delete( UUID projectUUID ) {
		
		final String sql = "DELETE FROM t_facet_items_project WHERE project_uuid = ?";
		Object[] args = {
				projectUUID,
		};
		
		this.jdbc.update( sql, args );
	}
    
    public void bulkinsert( final UUID projectUUID
			// facet 検索用データ
			, final String[] speciesItems
			, final String[] devicesItems) {
		
		List<Object[]> batchArgs = new ArrayList<>();
		
		// ■species:
		for ( final String item : speciesItems ) {
			final String code = SystemCode.FACET_ITEM_CATEGORY.SPECIES;
			
			Object[] args = { projectUUID, code, item };
			batchArgs.add( args );
		}
		
		// ■devices:
		for ( final String item : devicesItems ) {
			final String code = SystemCode.FACET_ITEM_CATEGORY.DEVICES;
			
			Object[] args = { projectUUID, code, item };
			batchArgs.add( args );
		}
		
		// bulk-insert.
		final String sql = "INSERT INTO t_facet_items_project( project_uuid, category, item ) VALUES ( ?, ?, ? )";
		this.jdbc.batchUpdate( sql, batchArgs );
	}

}
