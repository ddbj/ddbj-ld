package ddbjld.api.app.service.dao;

import static ddbjld.api.common.utility.SpringJdbcUtil.*;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * アカウントテーブルのDAOクラス.
 *
 * @author m.tsumura
 *
 **/
@Repository
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AccountDao {
	
	public static class TAccountDataReader extends MapDataReader {

		// 疑似Entityクラス
		
		public TAccountDataReader() {}
		public TAccountDataReader( Map<String, Object> data ) {
			super( data );
		}
		public TAccountDataReader bind( Map<String, Object> data ) {
			super.data = data;
			return this;
		}
		
		
		public UUID uuid() {
			return super.uuid( "uuid" );
		}
		
		public String uid() {
			return super.string( "uid" );
		}
		
		public String refreshToken() {
			return super.string( "refresh_token" );
		}
	}
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UUID create(String uid, String refreshToken) {
        final var sql = "INSERT INTO t_account "
        		+ "VALUES ( gen_random_uuid(), ?, ? )"
        		+ "RETURNING uuid";

        Object[] args = {
                uid,
                refreshToken,
        };

    	Map<String, Object> returned = this.jdbcTemplate.queryForMap( sql, args );
    	return (UUID)returned.get( "uuid" );
    }
    /**
     * アカウントの情報を登録するメソッド.
     *
     * @param uuid
     * @param uid
     * @param refreshToken
     *
     * @return void
     *
     **/
    public void create(UUID uuid, String uid, String refreshToken) {
        final var sql = "INSERT INTO t_account VALUES (?, ?, ?)";

        Object[] args = {
                uuid,
                uid,
                refreshToken,
        };

        jdbcTemplate.update(sql, args);
    }

    /**
     * uuidをキーに取得するメソッド.
     *
     * @param uuid
     *
     * @return アカウント情報
     *
     **/
    public Map<String, Object> read(UUID uuid) {
     
    	final var sql = "SELECT * FROM t_account WHERE uuid = ?";
        Object[] args = {
                uuid
        };

		return MapQuery.one( jdbcTemplate, sql, args );
    }

    /**
     * uidをキーに取得するメソッド.
     *
     * @param uid
     *
     * @return アカウント情報
     *
     **/
    public Map<String, Object> readByUid(String uid) {

    	final var sql = "SELECT * FROM t_account WHERE uid = ?";
        Object[] args = {
                uid
        };

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
    public boolean exists(UUID uuid) {

    	final var sql = "SELECT uuid FROM t_account WHERE uuid = ?";
        Object[] args = {
                uuid
        };

		return MapQuery.exists( jdbcTemplate, sql, args );
    }

    /**
     * uidをキーに存在するか確認するメソッド.
     *
     * @param uid
     *
     * @return 判定結果
     *
     **/
    public boolean existsByUid(String uid) {
        final var sql = "SELECT uuid FROM t_account WHERE uid = ?";

        Object[] args = {
                uid
        };

		return MapQuery.exists( jdbcTemplate, sql, args );
    }

    /**
     * uuidをキーにリフレッシュトークンを更新するメソッド.
     *
     * @param uuid
     * @param refreshToken
     *
     **/
    public void updateRefreshToken(UUID uuid, String refreshToken) {
        final var sql = "UPDATE t_account SET refresh_token = ? WHERE uuid = ?";

        Object[] args = {
                refreshToken,
                uuid
        };

        jdbcTemplate.update(sql, args);
    }

    /**
     * uidをキーにリフレッシュトークンを更新するメソッド.
     *
     * @param uid
     * @param refreshToken
     *
     **/
    public void updateRefreshTokenByUid(String uid, String refreshToken) {
        final var sql = "UPDATE t_account SET refresh_token = ? WHERE uid = ?";

        Object[] args = {
                refreshToken,
                uid
        };

        jdbcTemplate.update(sql, args);
    }
}
