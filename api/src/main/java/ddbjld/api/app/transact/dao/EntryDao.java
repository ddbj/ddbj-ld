package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.EntryEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class EntryDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public EntryEntity read(final UUID uuid) {
        var sql = "SELECT * FROM t_entry WHERE uuid = ?;";
        Object[] args = {
                uuid,
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return this.getEntry(row);
    }

    @Transactional(readOnly = true)
    public boolean exists(final UUID uuid, final int revision) {
        var sql = "SELECT * FROM t_entry WHERE uuid = ? AND revision = ?;";
        Object[] args = {
                uuid,
                revision
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    @Transactional(readOnly = true)
    public boolean existByUUID(final UUID uuid) {
        var sql = "SELECT * FROM t_entry WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public UUID create(
            final String title,
            final String description
    ) {
        var sql = "INSERT INTO t_entry" +
                "(uuid, title, description)" +
                "VALUES" +
                "(gen_random_uuid(), ?, ?)" +
                "RETURNING uuid";

        Object[] args = {
                title,
                description,
        };

        var returned = this.jvarJdbc.queryForMap(sql, args);

        return (UUID)returned.get("uuid");
    }

    // TODO シーケンスをベースに一意なラベルを発行するメソッド

    private EntryEntity getEntry(final Map<String, Object> row) {
        return new EntryEntity(
                (UUID)row.get("uuid"),
                (String)row.get("label"),
                (String) row.get("title"),
                (String) row.get("description"),
                (String) row.get("status"),
                (String) row.get("validation_status"),
                (String) row.get("metadata_json"),
                (String) row.get("aggregate_json"),
                (Boolean) row.get("editable"),
                (Integer) row.get("published_revision"),
                // FIXME TimestampからlocalDateTimeにコンバートするUtilに切り出す
                null == row.get("published_at") ? null : ((Timestamp) row.get("published_at")).toLocalDateTime(),
                ((Timestamp) row.get("created_at")).toLocalDateTime(),
                ((Timestamp) row.get("updated_at")).toLocalDateTime()
        );
    }
}
