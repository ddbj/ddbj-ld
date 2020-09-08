package ddbjld.api.app.service.dao;

import static ddbjld.api.common.utility.SpringJdbcUtil.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * プロジェクト権限テーブルのDAOクラス.
 *
 * @author m.tsumura
 *
 **/
@Repository
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ProjectRoleDao {
	
	public static class TProjectRoleDataReader extends MapDataReader {

		// 疑似Entityクラス
		
		public TProjectRoleDataReader() {}
		public TProjectRoleDataReader( Map<String, Object> data ) {
			super( data );
		}
		public TProjectRoleDataReader bind( Map<String, Object> data ) {
			super.data = data;
			return this;
		}
		
		public UUID projectUUID() {
			return super.uuid( "project_uuid" );
		}
		public UUID accountUUID() {
			return super.uuid( "account_uuid" );
		}
		
		public boolean owner() {
			return super.bool( "owner" );
		}
		public boolean writable() {
			return super.bool( "writable" );
		}
		public boolean readable() {
			return super.bool( "readable" );
		}
		
		
		@Deprecated
		public Date expireDate() {
			// FIXME： sql.Date はあまり使いたくないので LocalDate に変更予定。
			return (Date)column( this.data, "expire_date" );
		}
	}
	
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
	public void createOwner( UUID projectUuid, UUID accountUuid ) {
		
		// プロジェクト作成者＝所有者 の権限は [true/true/true] で固定、且つ無期限。
		Object[] args = {
				accountUuid,
				projectUuid,
				true,
				true,
				true,
				null,
		};

		this.create( args );
	}
	
	public void createEditor( UUID projectUuid, UUID accountUuid, LocalDate expireDate ) {
		
		// 編集者の場合は更新権限まで。
		Object[] args = {
				accountUuid,
				projectUuid,
				false,
				true,
				true,
				expireDate,
		};

		this.create( args );
	}
	
	@Deprecated // FIXME：【Pending】DDBJアカウント統合対応まで、閲覧者の仕様は凍結。
	public void createObserver( UUID projectUuid, UUID accountUuid, LocalDate expireDate ) {
		
		// 閲覧者の場合は参照権限のみ。
		Object[] args = {
				accountUuid,
				projectUuid,
				false,
				false,
				true, // observer は参照権限のみ。
				expireDate,
		};
		
		this.create( args );
	}
	private void create( Object[] args ) {
		
		final String sql = "INSERT INTO t_project_role"
				+ "( account_uuid, project_uuid, owner, writable, readable, expire_date )"
				+ "VALUES"
				+ "( ?, ?, ?, ?, ?, ? )";
		
		this.jdbcTemplate.update( sql, args );
	}

    /**
     * account_uuid, project_uuidをキーにプロジェクト権限情報を取得するメソッド.
     *
     * @param accountUuid
     * @param projectUuid
     *
     * @return プロジェクト権限情報
     *
     **/
    public Map<String, Object> read(UUID accountUuid, UUID projectUuid) {
    	
        final var sql = "SELECT * FROM t_project_role "
                + "WHERE account_uuid = ? "
                + "AND project_uuid = ?";
        
        Object[] args = {
                accountUuid,
                projectUuid
        };

		return MapQuery.one( jdbcTemplate, sql, args );
    }
    
    

    /**
     * account_idをキーにプロジェクト権限情報を取得するメソッド.
     *
     * @param accountUuid
     *
     * @return プロジェクト権限情報
     *
     **/
    public List<Map<String, Object>> readByAccountUuid(UUID accountUuid) {

    	final var sql = "SELECT * FROM t_project_role WHERE account_uuid = ?";
        Object[] args = {
                accountUuid
        };

        return MapQuery.all( jdbcTemplate, sql, args );
    }

    /**
     * project_uuidをキーにプロジェクト権限情報を取得するメソッド.
     *
     * @param projectUuid
     *
     * @return プロジェクト権限情報
     *
     **/
    public List<Map<String, Object>> readByProjectUuid(UUID projectUuid) {

    	final var sql = "SELECT * FROM t_project_role WHERE project_uuid = ?";
        Object[] args = {
                projectUuid
        };

        return MapQuery.all( jdbcTemplate, sql, args );
    }

    /**
     * アカウントが対象のプロジェクトに所有者権限を所持しているか判定するメソッド.
     *
     * @param accountUuid
     * @param projectUuid
     *
     * @return 判定結果
     *
     **/
    public boolean hasOwner(UUID accountUuid, UUID projectUuid) {
        final var sql = "SELECT * FROM t_project_role "
                + "WHERE account_uuid = ? "
                + "  AND project_uuid = ? "
                + "  AND owner = true";

		Object[] args = {
				accountUuid,
				projectUuid
		};

        return MapQuery.exists( jdbcTemplate, sql, args );
    }

	/**
	 * アカウントが対象のプロジェクトに編集権限を所持しているか判定するメソッド.
	 *
	 * @param accountUuid
	 * @param projectUuid
	 *
	 * @return 判定結果
	 *
	 **/
	public boolean hasWritable(UUID accountUuid, UUID projectUuid) {
		final var sql = "SELECT * FROM t_project_role "
				+ "WHERE account_uuid = ? "
				+ "  AND project_uuid = ? "
				+ "  AND writable = true";

		Object[] args = {
				accountUuid,
				projectUuid
		};

		return MapQuery.exists( jdbcTemplate, sql, args );
	}

	/**
	 * アカウントが対象のプロジェクトに読取権限を所持しているか判定するメソッド.
	 *
	 * @param accountUuid
	 * @param projectUuid
	 *
	 * @return 判定結果
	 *
	 **/
	public boolean hasReadable(UUID accountUuid, UUID projectUuid) {
		final var sql = "SELECT * FROM t_project_role "
				+ "WHERE account_uuid = ? "
				+ "  AND project_uuid = ? "
				+ "  AND readable = true"
				+ "  AND expire_date > current_date";

		Object[] args = {
				accountUuid,
				projectUuid
		};

		return MapQuery.exists( jdbcTemplate, sql, args );
	}
    
    /**
     * @param accountUuid
     * @param projectUuid
     * @return 現時点（DBサーバ日付）で有効な権限情報を取得。
     */
    public Map<String, Object> getAvailableRole(UUID accountUuid, UUID projectUuid) {
        final String sql = "SELECT * FROM t_project_role "
                + " WHERE account_uuid = ? "
                + "   AND project_uuid = ?"
                + "   AND expire_date > current_date";

        Object[] args = {
                accountUuid,
                projectUuid
        };

		return MapQuery.one( jdbcTemplate, sql, args );
    }

    /**
     * プロジェクト権限を編集者に更新するメソッド.
     *
	 * @param projectUuid
	 * @param accountUuid
     *
     * @return void
     *
     **/
    public void updateEditor(
			final UUID projectUuid,
			final UUID accountUuid,
			final LocalDate expireDate) {
        final var sql = "UPDATE t_project_role "
                + "SET owner = false "
				+ ", writable = true "
                + ", readable = true "
                + ", expire_date = ? "
                + "WHERE project_uuid = ? "
                + "AND account_uuid = ?";

        Object[] args = {
				projectUuid,
				accountUuid,
				expireDate
        };

        jdbcTemplate.update(sql, args);
    }
    
	public int updateProjectRoleEnabled( UUID projectUUID, UUID accountUUID, boolean enabled ) {
		final String sql 
				= "UPDATE t_project_role"
				+ "   SET enabled = ?"
				+ " WHERE project_uuid = ? "
				+ "   AND account_uuid = ?";
		
		Object[] args = {
				enabled,
				projectUUID,
				accountUUID,
		};
		return jdbcTemplate.update( sql, args );
	}

	public int deleteProjectRoleEnabled( UUID projectUUID, UUID accountUUID ) {
		final String sql 
				= "DELETE from t_project_role"
				+ " WHERE project_uuid = ? "
				+ "   AND account_uuid = ?";
		
		Object[] args = {
				projectUUID,
				accountUUID,
		};
		return jdbcTemplate.update( sql, args );
	}
}
