package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.HFileEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class HFileDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public HFileEntity read(final UUID uuid) {
        var sql = "SELECT * FROM h_file WHERE uuid = ?;";
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
    public List<HFileEntity> readEntryFiles(final UUID entryUUID) {
        var sql = "SELECT * FROM h_file WHERE entry_uuid = ?;";
        Object[] args = {
                entryUUID,
        };

        var rows = SpringJdbcUtil.MapQuery.all(this.jvarJdbc, sql, args);

        if(null == rows) {
            return null;
        }

        var entities = new ArrayList<HFileEntity>();

        for(var row: rows) {
            var entity = this.getEntity(row);

            entities.add(entity);
        }

        return entities;
    }

    @Transactional(readOnly = true)
    public HFileEntity readByName(
            final UUID entryUUID,
            final String name,
            final String type
    ) {
        var sql = "SELECT * FROM h_file " +
                "WHERE entry_uuid = ? " +
                "AND name = ? " +
                "AND type = ? ";
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
        var sql = "SELECT * FROM h_file WHERE entry_uuid = ?;";
        Object[] args = {
                entryUUID
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public void insert(
            final UUID uuid,
            final Integer revision,
            final UUID entryUUID,
            final long entryRevision,
            final String name,
            final String type,
            final UUID validationUUID,
            final String validationStatus,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime deletedAt
    ) {
        var sql = "INSERT INTO h_file " +
                "(uuid," +
                "revision," +
                "entry_uuid," +
                "entry_revision," +
                "name," +
                "type," +
                "validation_uuid," +
                "validation_status," +
                "created_at," +
                "updated_at," +
                "deleted_at)" +
                "VALUES" +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] args = {
                uuid,
                revision,
                entryUUID,
                entryRevision,
                name,
                type,
                validationUUID,
                validationStatus,
                createdAt,
                updatedAt,
                deletedAt,
        };

        this.jvarJdbc.update(sql, args);
    }

    public void update(
            final UUID uuid,
            final long revision,
            final long entryRevision,
            final UUID validationUUID,
            final String validationStatus
    ) {
        final var sql = "UPDATE h_file SET " +
                ",revision = ?" +
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
        var sql = "DELETE FROM h_file WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }


    private HFileEntity getEntity(final Map<String, Object> row) {
        return new HFileEntity(
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
