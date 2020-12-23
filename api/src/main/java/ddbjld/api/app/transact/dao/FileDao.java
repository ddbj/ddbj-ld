package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class FileDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public FileEntity read(final UUID uuid) {
        var sql = "SELECT * FROM t_file " +
                "WHERE uuid = ? " +
                "  AND deleted_at IS NULL;";

        Object[] args = {
                uuid,
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return this.getEntity(row);
    }

    @Transactional(readOnly = true)
    public List<FileEntity> readEntryFiles(final UUID entryUUID) {
        var sql = "SELECT * FROM t_file " +
                "WHERE entry_uuid = ? " +
                "  AND deleted_at IS NULL;";

        Object[] args = {
                entryUUID,
        };

        var rows = SpringJdbcUtil.MapQuery.all(this.jvarJdbc, sql, args);

        if(null == rows) {
            return null;
        }

        var entities = new ArrayList<FileEntity>();

        for(var row: rows) {
            var entity = this.getEntity(row);

            entities.add(entity);
        }

        return entities;
    }

    @Transactional(readOnly = true)
    public FileEntity readEntryWorkBook(final UUID entryUUID) {
        var sql = "SELECT * FROM t_file " +
                "WHERE entry_uuid = ?" +
                "  AND type = 'workbook'" +
                "  AND deleted_at IS NULL;";

        Object[] args = {
                entryUUID,
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        return this.getEntity(row);
    }

    @Transactional(readOnly = true)
    public FileEntity readByName(
            final UUID entryUUID,
            final String name,
            final String type
    ) {
        var sql = "SELECT * FROM t_file " +
                "WHERE entry_uuid = ? " +
                "  AND name = ? " +
                "  AND type = ? " +
                "  AND deleted_at IS NULL;";

        Object[] args = {
                entryUUID,
                name,
                type
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return this.getEntity(row);
    }

    @Transactional(readOnly = true)
    public boolean hasWorkBook(final UUID entryUUID) {
        var sql = "SELECT * FROM t_file " +
                "WHERE entry_uuid = ? " +
                "  AND type = 'workbook' " +
                "  AND deleted_at IS NULL " +
                "LIMIT 1;";

        Object[] args = {
                entryUUID
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    @Transactional(readOnly = true)
    public boolean hasOtherWorkBook(
            final UUID entryUUID,
            final String name
    ) {
        var sql = "SELECT * FROM t_file " +
                "WHERE entry_uuid = ? " +
                "  AND name != ? " +
                "  AND type = 'workbook' " +
                "  AND deleted_at IS NULL " +
                "LIMIT 1;";

        Object[] args = {
                entryUUID,
                name
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }


    @Transactional(readOnly = true)
    public boolean hasVCF(final UUID entryUUID) {
        var sql = "SELECT * FROM t_file " +
                "WHERE entry_uuid = ? " +
                "  AND type = 'vcf' " +
                "  AND deleted_at IS NULL " +
                "LIMIT 1;";
        Object[] args = {
                entryUUID
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public UUID create(
            final UUID entryUUID,
            final String name,
            final String type,
            final long entryRevision
    ) {
        var sql = "INSERT INTO t_file " +
                "(uuid, entry_uuid, name, type, entry_revision)" +
                "VALUES" +
                "(gen_random_uuid(), ?, ?, ?, ?)" +
                "RETURNING uuid";

        Object[] args = {
                entryUUID,
                name,
                type,
                entryRevision
        };

        var returned = this.jvarJdbc.queryForMap(sql, args);

        return (UUID)returned.get("uuid");
    }

    public void update(
            final UUID uuid,
            final long revision,
            final long entryRevision,
            final UUID validationUUID,
            final String validationStatus
    ) {
        final var sql = "UPDATE t_file SET " +
                " revision = ?" +
                ",entry_revision = ?" +
                ",validation_uuid = ?" +
                ",validation_status = ?" +
                ",updated_at = CURRENT_TIMESTAMP" +
                " WHERE uuid = ?";
        Object[] args = {
                revision,
                entryRevision,
                validationUUID,
                validationStatus,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void delete(final UUID uuid) {
        var file          = this.read(uuid);
        var entryRevision = file.getEntryRevision() + 1;

        final var sql = "UPDATE t_file SET " +
                " entry_revision = ? " +
                ",deleted_at = CURRENT_TIMESTAMP " +
                ",updated_at = CURRENT_TIMESTAMP " +
                " WHERE uuid = ?";
        Object[] args = {
                entryRevision,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }


    private FileEntity getEntity(final Map<String, Object> row) {
        return new FileEntity(
                (UUID)row.get("uuid"),
                (Integer) row.get("revision"),
                (UUID)row.get("entry_uuid"),
                (Integer) row.get("entry_revision"),
                (String)row.get("name"),
                (String)row.get("type"),
                (UUID)row.get("validation_uuid"),
                (String)row.get("validation_status"),
                ((Timestamp) row.get("created_at")).toLocalDateTime(),
                ((Timestamp) row.get("updated_at")).toLocalDateTime(),
                null == row.get("deleted_at") ? null : ((Timestamp) row.get("deleted_at")).toLocalDateTime()
        );
    }
}
