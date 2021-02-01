package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.RequestEntity;
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
public class RequestDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public RequestEntity read(final UUID uuid) {
        var sql = "SELECT * FROM t_request WHERE uuid = ?;";
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
    public List<RequestEntity> readEntryAllRequests(final UUID entryUUID) {
        var sql = "SELECT * FROM t_request " +
                "WHERE entry_uuid = ?;";
        Object[] args = {
                entryUUID,
        };

        var rows = SpringJdbcUtil.MapQuery.all(this.jvarJdbc, sql, args);

        if(null == rows) {
            return null;
        }

        var entities = new ArrayList<RequestEntity>();

        for(var row: rows) {
            var entity = this.getEntity(row);

            entities.add(entity);
        }

        return entities;
    }

    @Transactional(readOnly = true)
    public boolean hasNoActiveRequest(final UUID entryUUID) {
        var sql = "SELECT * FROM t_request " +
                "WHERE entry_uuid = ? " +
                "  AND status = 'Open' " +
                "LIMIT 1;";
        Object[] args = {
                entryUUID
        };

        return false == SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public UUID create(
            final UUID entryUUID,
            final Integer entryRevision,
            final UUID accountUUID,
            final String type,
            final String comment
            ) {
        var sql = "INSERT INTO t_request" +
                "(uuid, entry_uuid, entry_revision, account_uuid, type, comment)" +
                "VALUES" +
                "(gen_random_uuid(), ?, ?, ?, ?, ?)" +
                "RETURNING uuid";

        Object[] args = {
                entryUUID,
                entryRevision,
                accountUUID,
                type,
                comment,
        };

        var returned = this.jvarJdbc.queryForMap(sql, args);

        return (UUID)returned.get("uuid");
    }

    public void updateComment(
            final UUID uuid,
            final String comment
    ) {
        final var sql = "UPDATE t_request SET " +
                "comment = ?, " +
                "updated_at = CURRENT_TIMESTAMP " +
                "WHERE uuid = ?";
        Object[] args = {
                comment,
                uuid,
        };

        this.jvarJdbc.update(sql, args);
    }

    public void close(
            final UUID uuid
    ) {
        final var sql = "UPDATE t_request SET " +
                "status = 'Close', " +
                "updated_at = CURRENT_TIMESTAMP " +
                "WHERE uuid = ?";
        Object[] args = {
                uuid,
        };

        this.jvarJdbc.update(sql, args);
    }

    public void cancel(
            final UUID uuid
    ) {
        final var sql = "UPDATE t_request SET " +
                "status = 'Cancel', " +
                "updated_at = CURRENT_TIMESTAMP " +
                "WHERE uuid = ?";
        Object[] args = {
                uuid,
        };

        this.jvarJdbc.update(sql, args);
    }

    private RequestEntity getEntity(final Map<String, Object> row) {
        return new RequestEntity(
                (UUID)row.get("uuid"),
                (UUID)row.get("entry_uuid"),
                (Integer)row.get("entry_revision"),
                (UUID)row.get("account_uuid"),
                (String)row.get("type"),
                (String)row.get("comment"),
                (String)row.get("status"),
                ((Timestamp) row.get("created_at")).toLocalDateTime(),
                ((Timestamp) row.get("updated_at")).toLocalDateTime()
        );
    }
}
