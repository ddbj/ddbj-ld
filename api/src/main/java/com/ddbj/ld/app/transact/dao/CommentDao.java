package com.ddbj.ld.app.transact.dao;

import com.ddbj.ld.common.utility.SpringJdbcUtil;
import com.ddbj.ld.data.entity.CommentEntity;
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
public class CommentDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public CommentEntity read(final UUID uuid) {
        var sql = "SELECT * FROM t_comment WHERE uuid = ?;";
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
    public List<CommentEntity> readEntryAllComments(final UUID entryUUID) {
        var sql = "SELECT * FROM t_comment " +
                "WHERE entry_uuid = ?;";
        Object[] args = {
                entryUUID,
        };

        var rows = SpringJdbcUtil.MapQuery.all(this.jvarJdbc, sql, args);

        if(null == rows) {
            return null;
        }

        var entities = new ArrayList<CommentEntity>();

        for(var row: rows) {
            var entity = this.getEntity(row);

            entities.add(entity);
        }

        return entities;
    }

    @Transactional(readOnly = true)
    public List<CommentEntity> readEntryComments(final UUID entryUUID) {
        var sql = "SELECT * FROM t_comment " +
                "WHERE entry_uuid = ? " +
                "AND curator = false;";
        Object[] args = {
                entryUUID,
        };

        var rows = SpringJdbcUtil.MapQuery.all(this.jvarJdbc, sql, args);

        if(null == rows) {
            return null;
        }

        var entities = new ArrayList<CommentEntity>();

        for(var row: rows) {
            var entity = this.getEntity(row);

            entities.add(entity);
        }

        return entities;
    }

    public UUID create(
            final UUID entryUUID,
            final UUID accountUUID,
            final String comment,
            final boolean curator
            ) {
        var sql = "INSERT INTO t_comment" +
                "(uuid, entry_uuid, account_uuid, comment, curator)" +
                "VALUES" +
                "(gen_random_uuid(), ?, ?, ?, ?)" +
                "RETURNING uuid";

        Object[] args = {
                entryUUID,
                accountUUID,
                comment,
                curator
        };

        var returned = this.jvarJdbc.queryForMap(sql, args);

        return (UUID)returned.get("uuid");
    }

    public void update(
            final UUID uuid,
            final String comment,
            final boolean curator
    ) {
        final var sql = "UPDATE t_comment SET " +
                "comment = ?, " +
                "curator = ?, " +
                "updated_at = CURRENT_TIMESTAMP " +
                "WHERE uuid = ?";
        Object[] args = {
                comment,
                curator,
                uuid,
        };

        this.jvarJdbc.update(sql, args);
    }

    public void delete(final UUID uuid) {
        var sql = "DELETE FROM t_comment WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    private CommentEntity getEntity(final Map<String, Object> row) {
        return new CommentEntity(
                (UUID)row.get("uuid"),
                (UUID)row.get("entry_uuid"),
                (UUID)row.get("account_uuid"),
                (Boolean) row.get("curator"),
                (String)row.get("comment"),
                ((Timestamp) row.get("created_at")).toLocalDateTime(),
                ((Timestamp) row.get("updated_at")).toLocalDateTime()
        );
    }
}
