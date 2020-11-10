package ddbjld.api.app.service.dao;

import static ddbjld.api.common.utility.SpringJdbcUtil.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ファイルテーブルのDAOクラス.
 *
 * @author m.tsumura
 *
 **/
@Repository
@Slf4j
public class FileDao {

    public static class TFileDataReader extends MapDataReader {

        // 疑似Entityクラス

        public TFileDataReader() {}
        public TFileDataReader( Map<String, Object> data ) {
            super( data );
        }
        public TFileDataReader bind(Map<String, Object> data ) {
            super.data = data;
            return this;
        }

        public UUID uuid() {
            return super.uuid( "uuid" );
        }
        public UUID projectUuid() {
            return super.uuid( "project_uuid" );
        }

        public String type() {
            return super.string( "type" );
        }

        public String name() {
            return super.string( "name" );
        }
    }
	
    @Autowired @Qualifier("jvarJdbc")
    private JdbcTemplate jdbcTemplate;

    public UUID create(UUID projectUuid, String type, String name) {

        final String sql = "INSERT INTO t_file"
                + "( uuid, project_uuid, type, name )"
                + "VALUES"
                + "( gen_random_uuid(), ?, ?, ? )"
                + "RETURNING uuid";

        Object[] args = {
                projectUuid,
                type,
                name
        };

        Map<String, Object> returned = this.jdbcTemplate.queryForMap( sql, args );
        return (UUID)returned.get( "uuid" );
    }

    /**
     * uuidをキーに取得するメソッド.
     *
     * @param uuid
     *
     * @return ファイル情報
     *
     **/
    public Map<String, Object> read(UUID uuid) {

        final var sql = "SELECT * FROM t_file WHERE uuid = ?";
        Object[] args = { uuid };

        return MapQuery.one( jdbcTemplate, sql, args );
    }

    /**
     * project_uuidをキーに取得するメソッド.
     *
     * @param projectUuid
     *
     * @return ファイル情報
     *
     **/
    public List<Map<String, Object>> readByProjectUuid(UUID projectUuid) {

        final var sql = "SELECT * FROM t_file WHERE project_uuid = ?";
        Object[] args = { projectUuid };

        return MapQuery.all(jdbcTemplate, sql, args );
    }
    /**
     * project_uuid, type, nameをキーに取得するメソッド.
     *
     * @param projectUuid
     * @param type
     * @param name
     *
     * @return 判定結果
     *
     **/
    public Map<String, Object> readStrict(UUID projectUuid, String type, String name) {

        final var sql = "SELECT * FROM t_file"
                + " WHERE project_uuid = ?"
                + "   AND type = ?"
                + "   AND name = ?";

        Object[] args = {
                projectUuid,
                type,
                name
        };

        return MapQuery.one(jdbcTemplate, sql, args);
    }


    /**
     * uuidをキーに削除するメソッド.
     *
     * @param uuid
     *
     * @return void
     *
     **/
    public void delete(UUID uuid) {

        final var sql = "DELETE FROM t_file WHERE uuid = ?";
        Object[] args = { uuid };

        jdbcTemplate.update(sql, args);
    }

    /**
     * project_uuid, type, nameをキーに存在するか確認するメソッド.
     *
     * @param projectUuid
     * @param type
     * @param name
     *
     * @return 判定結果
     *
     **/
    public boolean exists(UUID projectUuid, String type, String name) {

        final var sql = "SELECT * FROM t_file"
                + " WHERE project_uuid = ?"
                + "   AND type = ?"
                + "   AND name = ?";

        Object[] args = {
                projectUuid,
                type,
                name
        };

        return MapQuery.exists( jdbcTemplate, sql, args );
    }
}
