package ddbjld.api.app.service.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

import static ddbjld.api.common.utility.SpringJdbcUtil.MapDataReader;
import static ddbjld.api.common.utility.SpringJdbcUtil.MapQuery;

/**
 * アップロードテーブルのDAOクラス.
 *
 * @author m.tsumura
 *
 **/
@Repository
@Slf4j
public class UploadDao {

    public static class TUploadDataReader extends MapDataReader {

        // 疑似Entityクラス

        public TUploadDataReader() {}
        public TUploadDataReader( Map<String, Object> data ) {
            super( data );
        }
        public TUploadDataReader bind(Map<String, Object> data ) {
            super.data = data;
            return this;
        }

        public UUID uuid() {
            return super.uuid( "uuid" );
        }
        public UUID accountUuid() {
            return super.uuid( "account_uuid" );
        }
        public UUID projectUuid() {
            return super.uuid( "project_uuid" );
        }
        public String fileType() { return super.string( "file_type" ); }
        public String fileName() {
            return super.string( "file_name" );
        }
    }
	
    @Autowired @Qualifier("jvarJdbc")
    private JdbcTemplate jdbcTemplate;

    public UUID create(UUID accountUuid, UUID projectUuid, String fileType, String fileName) {

        final String sql = "INSERT INTO t_upload"
                + "( uuid, token, account_uuid, project_uuid, file_type, file_name )"
                + "VALUES"
                + "( gen_random_uuid(), gen_random_uuid(), ?, ?, ?, ? )"
                + "RETURNING token";

        Object[] args = {
                accountUuid,
                projectUuid,
                fileType,
                fileName
        };

        Map<String, Object> returned = this.jdbcTemplate.queryForMap( sql, args );
        return (UUID)returned.get( "token" );
    }

    /**
     * tokenをキーに取得するメソッド.
     *
     * @param token
     *
     * @return レコード
     *
     **/
    public Map<String, Object> read(UUID token) {

        final var sql = "SELECT * FROM t_upload"
                + " WHERE token = ?";

        Object[] args = {
                token
        };

        return MapQuery.one(jdbcTemplate, sql, args);
    }

    /**
     * tokenをキーに削除するメソッド.
     *
     * @param token
     *
     * @return void
     *
     **/
    public void delete(UUID token) {

        final var sql = "DELETE FROM t_upload WHERE token = ?";
        Object[] args = { token };

        jdbcTemplate.update(sql, args);
    }

    /**
     * tokenをキーに有効なレコードが存在するか確認するメソッド.
     *
     * @param token
     *
     * @return 判定結果
     *
     **/
    public boolean exists(UUID token) {

        final var sql = "SELECT * FROM t_upload"
                + " WHERE token = ?";

        Object[] args = {
                token
        };

        return MapQuery.exists( jdbcTemplate, sql, args );
    }

    /**
     * 他のユーザーがアップロード中か確認するメソッド.
     *
     * @param accountUuid
     * @param projectUuid
     * @param fileType
     * @param fileName
     *
     * @return 判定結果
     *
     **/
    public boolean isUploading(
            UUID accountUuid,
            UUID projectUuid,
            String fileType,
            String fileName) {

        final var sql = "SELECT * FROM t_upload"
                + " WHERE account_uuid != ?"
                + " AND project_uuid = ?"
                + " AND file_type = ?"
                + " AND file_name = ?";

        Object[] args = {
                accountUuid,
                projectUuid,
                fileType,
                fileName
        };

        return MapQuery.exists( jdbcTemplate, sql, args );
    }
}
