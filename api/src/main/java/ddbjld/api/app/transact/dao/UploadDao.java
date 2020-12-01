package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.UploadEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class UploadDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public UploadEntity read(final UUID uuid) {
        var sql = "SELECT * FROM t_upload WHERE uuid = ?;";
        Object[] args = {
                uuid,
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return this.getEntity(row);
    }

    public UUID create(
            final UUID fileUUID
            ) {
        var sql = "INSERT INTO t_upload" +
                "(uuid, file_uuid)" +
                "VALUES" +
                "(gen_random_uuid(), ?)" +
                "RETURNING uuid";

        Object[] args = {
                fileUUID
        };

        var returned = this.jvarJdbc.queryForMap(sql, args);

        return (UUID)returned.get("uuid");
    }

    public void update(
            final UUID uuid,
            final boolean ended
    ) {
        final var sql = "UPDATE t_upload SET ended = ? WHERE uuid = ?";
        Object[] args = {
                ended,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void delete(final UUID uuid) {
        var sql = "DELETE FROM t_upload WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public boolean existActiveToken(final UUID uuid) {
        var sql = "SELECT * FROM t_upload WHERE uuid = ? AND ended = false;";
        Object[] args = {
                uuid,
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public boolean existActiveTokenByFileUUID(final UUID fileUUID) {
        var sql = "SELECT * FROM t_upload WHERE file_uuid = ? " +
                "AND ended = false;";
        Object[] args = {
                fileUUID,
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    private UploadEntity getEntity(final Map<String, Object> row) {
        return new UploadEntity(
                (UUID)row.get("uuid"),
                (UUID)row.get("file_uuid"),
                (boolean)row.get("ended")
        );
    }
}
