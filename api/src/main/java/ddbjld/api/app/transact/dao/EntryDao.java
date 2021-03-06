package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.EntryEntity;
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
public class EntryDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public EntryEntity read(final UUID uuid) {
        var sql = "SELECT * FROM t_entry " +
                "WHERE uuid = ? " +
                "AND deleted_at IS NULL;";
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
    public EntryEntity readAny(final UUID uuid) {
        var sql = "SELECT * FROM t_entry " +
                "WHERE uuid = ?;";
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
    public List<EntryEntity> all() {
        var sql = "SELECT * FROM t_entry " +
                "WHERE deleted_at IS NULL;";

        var rows = SpringJdbcUtil.MapQuery.all(this.jvarJdbc, sql);

        if(null == rows) {
            return null;
        }

        var entities = new ArrayList<EntryEntity>();

        for(var row: rows) {
            var entity = this.getEntity(row);

            entities.add(entity);
        }

        return entities;
    }

    @Transactional(readOnly = true)
    public boolean exists(final UUID uuid, final int revision) {
        var sql = "SELECT * FROM t_entry " +
                "WHERE uuid = ? " +
                "  AND revision = ? " +
                "  AND deleted_at IS NULL;";
        Object[] args = {
                uuid,
                revision
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    @Transactional(readOnly = true)
    public boolean existByUUID(final UUID uuid) {
        var sql = "SELECT * FROM t_entry " +
                "WHERE uuid = ?" +
                "  AND deleted_at IS NULL;";
        Object[] args = {
                uuid
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    @Transactional(readOnly = true)
    public boolean existUpdateToken(final UUID uuid, final UUID updateToken ) {
        var sql = "SELECT * FROM t_entry " +
                "WHERE uuid = ? " +
                "  AND update_token = ? " +
                "  AND deleted_at IS NULL;";
        Object[] args = {
                uuid,
                updateToken
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public UUID create(final String type) {

        var sql = "INSERT INTO t_entry" +
                "(uuid, label, type, update_token)" +
                "VALUES" +
                "(gen_random_uuid(), ?, ?, gen_random_uuid())" +
                "RETURNING uuid";

        // シーケンスからラベル用の番号を取得
        var seq     = "SELECT NEXTVAL('entry_label_seq')";
        var row     = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, seq);
        var label   = "VSUB" + (long)row.get("nextval");

        Object[] args = {
                label,
                type
        };

        var returned = this.jvarJdbc.queryForMap(sql, args);

        return (UUID)returned.get("uuid");
    }

    public void updateRevision(final UUID uuid) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        var sql = "UPDATE t_entry SET revision = ? WHERE uuid = ?;";
        Object[] args = {
                revision,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void updateStatus(
            final UUID uuid,
            final String status
    ) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        var sql = "UPDATE t_entry SET revision = ?, status = ? WHERE uuid = ?;";
        Object[] args = {
                revision,
                status,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void updateValidationStatus(
            final UUID uuid,
            final String validationStatus
    ) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        var sql = "UPDATE t_entry SET revision = ?, validation_status = ? WHERE uuid = ?;";
        Object[] args = {
                revision,
                validationStatus,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void updateValidationStatus(
            final UUID uuid,
            final String validationStatus,
            final String metadataJson
    ) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        var sql = "UPDATE t_entry SET revision = ?, validation_status = ?, metadata_json = ? WHERE uuid = ?;";
        Object[] args = {
                revision,
                validationStatus,
                metadataJson,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void resetStatus(
            final UUID uuid
    ) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        var sql = "UPDATE t_entry SET revision = ?, status = 'Unsubmitted', validation_status = 'Unvalidated' WHERE uuid = ?;";
        Object[] args = {
                revision,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void submit(final UUID uuid) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        var sql = "UPDATE t_entry SET status = 'Submitted', revision = ? WHERE uuid = ?;";
        Object[] args = {
                revision,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void publish(final UUID uuid) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        var sql = "UPDATE t_entry SET " +
                    " revision = ? " +
                    ",status = 'Public' " +
                    ",editable = false " +
                    ",published_at = CURRENT_TIMESTAMP " +
                    ",updated_at = CURRENT_TIMESTAMP " +
                "WHERE uuid = ?;";
        Object[] args = {
                revision,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void unsubmit(final UUID uuid) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        var sql = "UPDATE t_entry SET " +
                    " revision = ? " +
                    ",status = 'Unsubmitted' " +
                    ",editable = true " +
                    ",published_at = null " +
                    ",updated_at = CURRENT_TIMESTAMP " +
                "WHERE uuid = ?;";
        Object[] args = {
                revision,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void toPrivate(final UUID uuid) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        var sql = "UPDATE t_entry SET " +
                " revision = ? " +
                ",status = 'Private' " +
                ",published_at = null " +
                ",updated_at = CURRENT_TIMESTAMP " +
                "WHERE uuid = ?;";
        Object[] args = {
                revision,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void toCancel(final UUID uuid) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;
        var status   = entry.getStatus().equals("Submitted") ? "Unsubmitted" : "Cancel";

        var sql = "UPDATE t_entry SET " +
                " revision = ? " +
                ",status = ? " +
                ",published_at = null " +
                ",updated_at = CURRENT_TIMESTAMP " +
                "WHERE uuid = ?;";
        Object[] args = {
                revision,
                status,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public boolean isUnsubmitted(final UUID uuid) {
        var sql = "SELECT * FROM t_entry " +
                "WHERE uuid = ? " +
                "  AND status = 'Unsubmitted'" +
                "  AND deleted_at IS NULL;";
        Object[] args = {
                uuid
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public void delete(final UUID uuid) {
        var sql = "DELETE FROM t_entry WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void deleteLogically(final UUID uuid) {
        var entry    = this.read(uuid);
        var revision = entry.getRevision() + 1;

        final var sql = "UPDATE t_entry SET " +
                " revision = ? " +
                ",deleted_at = CURRENT_TIMESTAMP " +
                ",updated_at = CURRENT_TIMESTAMP " +
                " WHERE uuid = ?";
        Object[] args = {
                revision,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    private EntryEntity getEntity(final Map<String, Object> row) {
        return new EntryEntity(
                (UUID)row.get("uuid"),
                (Integer)row.get("revision"),
                (String)row.get("label"),
                (String)row.get("type"),
                (String) row.get("status"),
                (String) row.get("validation_status"),
                (String) row.get("metadata_json"),
                (String) row.get("aggregate_json"),
                (Boolean) row.get("editable"),
                (UUID) row.get("update_token"),
                (Integer) row.get("published_revision"),
                // FIXME TimestampからlocalDateTimeにコンバートするUtilに切り出す
                null == row.get("published_at") ? null : ((Timestamp) row.get("published_at")).toLocalDateTime(),
                ((Timestamp) row.get("created_at")).toLocalDateTime(),
                ((Timestamp) row.get("updated_at")).toLocalDateTime()
        );
    }
}
