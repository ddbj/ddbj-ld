package ddbjld.api.app.service.dao;

import static ddbjld.api.common.utility.SpringJdbcUtil.*;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.common.exceptions.RestApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

/**
 * ユーザーテーブルのDAOクラス.
 *
 * @author m.tsumura
 *
 **/
@Repository
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserDao {
	
	public static class TUserDataReader extends MapDataReader {
		
		// 疑似Entityクラス
		
		public TUserDataReader() {}
		public TUserDataReader( Map<String, Object> data ) {
			super( data );
		}
		public TUserDataReader bind( Map<String, Object> data ) {
			super.data = data;
			return this;
		}
		
		public UUID uuid() {
			return super.uuid( "uuid" );
		}
		public UUID accountUuid() {
			return super.uuid( "account_uuid" );
		}

		public String profileJson() {
			return super.string( "profile_json" );
		}
		
		public boolean admin() {
			return super.bool( "admin" );
		}
	}
	
    @Autowired @Qualifier("jvarJdbc")
    private JdbcTemplate jdbcTemplate;

    public UUID create(UUID accountUuid) {

        final String sql = "INSERT INTO t_user"
                + "( uuid, account_uuid )"
                + "VALUES"
                + "( gen_random_uuid(), ? )"
                + "RETURNING uuid";

        Object[] args = {
                accountUuid,
        };

    	Map<String, Object> returned = this.jdbcTemplate.queryForMap( sql, args );
    	return (UUID)returned.get( "uuid" );
    }
    /**
     * 登録するメソッド.
     *
     * @param uuid
     * @param accountUuid
     * @param profileJson
     * @param admin
     *
     * @return void
     *
     **/
    public void create(UUID uuid, UUID accountUuid, String profileJson, boolean admin) {

        final String sql = "INSERT INTO t_user"
                + "( uuid, account_uuid, profile_json, admin )"
                + "VALUES"
                + "( ?, ?, ?, ? )";

        Object[] args = {
                uuid,
                accountUuid,
                profileJson,
                admin
        };

        this.jdbcTemplate.update( sql, args );
    }

    /**
     * uuidをキーに取得するメソッド.
     *
     * @param uuid
     *
     * @return ユーザー情報
     *
     **/
    public Map<String, Object> read(UUID uuid) {

    	final var sql = "SELECT * FROM t_user WHERE uuid = ?";
        Object[] args = { uuid };

		return MapQuery.one( jdbcTemplate, sql, args );
    }

    /**
     * account_uuidをキーに取得するメソッド.
     *
     * @param accountUuid
     *
     * @return ユーザー情報
     *
     **/
    public Map<String, Object> readByAccountUuid(UUID accountUuid) {

    	final var sql = "SELECT * FROM t_user WHERE account_uuid = ?";
        Object[] args = { accountUuid };

		return MapQuery.one( jdbcTemplate, sql, args );
    }

    /**
     * uuidをキーに存在するか確認するメソッド.
     *
     * @param uuid
     *
     * @return 判定結果
     *
     **/
    public boolean existsUser(UUID uuid) {

    	final var sql = "SELECT uuid FROM t_user WHERE uuid = ?";
        Object[] args = { uuid };

		return MapQuery.exists( jdbcTemplate, sql, args );
    }

    /**
     * uuidをキーに存在するか確認するメソッド.
     *
     * @param accountUuid
     *
     * @return 判定結果
     *
     **/
    public boolean existsUserByAccountUuid(UUID accountUuid) {
    	final var sql = "SELECT uuid FROM t_user WHERE account_uuid = ?";
        Object[] args = { accountUuid };

		return MapQuery.exists( jdbcTemplate, sql, args );
    }

    /**
     * アカウントUUIDをキーにプロフィールを更新するメソッド.
     *
     * @param uuid
     * @param profileJson
     *
     **/
    public void updateProfile(UUID uuid, String profileJson) {
        final var sql = "UPDATE t_user SET profile_json = ? WHERE uuid = ?";

        Object[] args = {
                profileJson,
                uuid
        };

        jdbcTemplate.update(sql, args);
    }

    /**
     * プロフィールを更新するメソッド.
     *
     * @param accountUuid
     * @param profileJson
     *
     **/
    public void updateProfileByAccountUUID(UUID accountUuid, String profileJson) {
        final var sql = "UPDATE t_user SET profile_json = ? WHERE account_uuid = ?";

        Object[] args = {
                profileJson,
                accountUuid
        };

        jdbcTemplate.update(sql, args);
    }

    /**
     * 管理者権限を更新するメソッド.
     *
     * @param uuid
     * @param admin
     *
     **/
    public void updateAdminable(UUID uuid, boolean admin) {
        final var sql = "UPDATE t_user SET admin = ? WHERE uuid = ?";

        Object[] args = { admin, uuid };

        jdbcTemplate.update(sql, args);
    }
    

	public TUserDataReader validateAdminableUser( UUID accountUuid ) {
		// accountがあってuserがない状態が通常ありえないが 401:UNAUTHORIZED にしておく。
		var data = this.readByAccountUuid( accountUuid );
		if ( null == data ) throw new RestApiException( HttpStatus.UNAUTHORIZED );
		// adminフラグが立っていない場合は権限不足として 403:FORBIDDEN を投げる。
		var user = new TUserDataReader( data );
		if ( !user.admin() ) throw new RestApiException( HttpStatus.FORBIDDEN );
		// adminフラグが立っている場合はt_userのデータを返す。
		return user;
	}

    /**
     * account_uuidをキーに管理者権限を確認するメソッド.
     *
     * @param accountUuid
     *
     * @return 判定結果
     *
     **/
    public boolean hasAdminByAccountUuid(UUID accountUuid) {
        final var sql = "SELECT uuid FROM t_user"
                + " WHERE account_uuid = ?"
                + "   AND admin = true";
        Object[] args = { accountUuid };

        return MapQuery.exists( jdbcTemplate, sql, args );
    }
}
