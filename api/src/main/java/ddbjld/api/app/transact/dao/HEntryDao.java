package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.HEntryEntity;
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
public class HEntryDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public HEntryEntity read(final UUID uuid, final int revision) {
        var sql = "SELECT * FROM h_entry WHERE uuid = ? AND revision = ?;";
        Object[] args = {
                uuid,
                revision
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return this.getEntity(row);
    }

    @Transactional(readOnly = true)
    public List<HEntryEntity> readByUUID(final UUID uuid) {
        var sql = "SELECT * FROM h_entry WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        var rows = SpringJdbcUtil.MapQuery.all(this.jvarJdbc, sql, args);

        if(null == rows) {
            return null;
        }

        var entities = new ArrayList<HEntryEntity>();

        for(var row: rows) {
            var entity = this.getEntity(row);
            entities.add(entity);
        }

        return entities;
    }

    @Transactional(readOnly = true)
    public boolean exists(final UUID uuid, final int revision) {
        var sql = "SELECT * FROM h_entry WHERE uuid = ? AND revision = ?;";
        Object[] args = {
                uuid,
                revision
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    @Transactional(readOnly = true)
    public boolean existByUUID(final UUID uuid) {
        var sql = "SELECT * FROM h_entry WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    @Transactional(readOnly = true)
    public long countByUUID(final UUID uuid) {
        var sql = "SELECT COUNT(*) AS cnt FROM h_entry WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        return null == row ? 0 : (long)row.get("cnt");
    }

<<<<<<< HEAD
    // isPublishedOnce
    @Transactional(readOnly = true)
    public boolean isPublishedOnce(final UUID uuid) {
        var sql = "SELECT COUNT(*) AS cnt FROM h_entry " +
                "WHERE uuid = ? " +
                "  AND published_at IS NOT NULL;";
        Object[] args = {
                uuid
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return false;
        }

        return 0 < (long)row.get("cnt");
    }

    public void insert(
        final UUID uuid,
        final int revision,
        final String label,
        final String type,
=======
    public void insert(
        final UUID uuid,
        final String label,
        final String title,
        final String description,
>>>>>>> 差分修正
        final String status,
        final String validationStatus,
        final String metadataJson,
        final String aggregateJson,
        final Boolean editable,
        final Integer publishedRevision,
<<<<<<< HEAD
        final LocalDateTime publishedAt,
        final String action
    ) {
        var sql = "INSERT INTO h_entry" +
                "(uuid, label, type, revision, status, validation_status, metadata_json, aggregate_json, editable, published_revision, published_at, action)" +
=======
        final LocalDateTime publishedAt
    ) {
        var revision = this.countByUUID(uuid) + 1;


        var sql = "INSERT INTO h_entry" +
                "(uuid, label, revision, title, description, status, validation_status, metadata_json, aggregate_json, editable, published_revision, published_at)" +
>>>>>>> 差分修正
                "VALUES" +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Object[] args = {
                uuid,
                label,
<<<<<<< HEAD
                type,
                revision,
=======
                revision,
                title,
                description,
>>>>>>> 差分修正
                status,
                validationStatus,
                metadataJson,
                aggregateJson,
                editable,
                publishedRevision,
<<<<<<< HEAD
                publishedAt,
                action
=======
                publishedAt
>>>>>>> 差分修正
        };

        this.jvarJdbc.update(sql, args);
    }

    public void deleteEntry(final UUID entryUUID) {
        var sql = "DELETE FROM h_entry WHERE uuid = ?;";
        Object[] args = {
                entryUUID
        };

        this.jvarJdbc.update(sql, args);
    }

    private HEntryEntity getEntity(final Map<String, Object> row) {
        return new HEntryEntity(
                (UUID)row.get("uuid"),
                (String)row.get("label"),
                (Integer) row.get("revision"),
<<<<<<< HEAD
                (String)row.get("type"),
=======
                (String) row.get("title"),
                (String) row.get("description"),
>>>>>>> 差分修正
                (String) row.get("status"),
                (String) row.get("validation_status"),
                (String) row.get("metadata_json"),
                (String) row.get("aggregate_json"),
                (Boolean) row.get("editable"),
                (Integer) row.get("published_revision"),
                // FIXME TimestampからlocalDateTimeにコンバートするUtilに切り出す
                null == row.get("published_at") ? null : ((Timestamp) row.get("published_at")).toLocalDateTime(),
                ((Timestamp) row.get("created_at")).toLocalDateTime(),
<<<<<<< HEAD
                ((Timestamp) row.get("updated_at")).toLocalDateTime(),
                (String) row.get("action")
=======
                ((Timestamp) row.get("updated_at")).toLocalDateTime()
>>>>>>> 差分修正
        );
    }
}
