package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.EntryRoleEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class EntryRoleDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public EntryRoleEntity read(final UUID accountUUID, final UUID entryUUID) {
        var sql = "SELECT * FROM t_entry_role WHERE account_uuid = ? AND entry_uuid = ?;";
        Object[] args = {
                accountUUID,
                entryUUID
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return this.getEntry(row);
    }

    @Transactional(readOnly = true)
    public List<EntryRoleEntity> readByAccountUUID(final UUID accountUUID) {
        var sql = "SELECT * FROM t_entry_role WHERE account_uuid = ?;";
        Object[] args = {
                accountUUID
        };

        var rows = SpringJdbcUtil.MapQuery.all(this.jvarJdbc, sql, args);

        if(null == rows) {
            return null;
        }

        var entities = new ArrayList<EntryRoleEntity>();

        for(var row: rows) {
            var entity = this.getEntry(row);

            entities.add(entity);
        }

        return entities;
    }

    @Transactional(readOnly = true)
    public boolean hasRole(final UUID accountUUID, final UUID entryUUID) {
        var sql = "SELECT * FROM t_entry_role WHERE account_uuid = ? AND entry_uuid = ?;";
        Object[] args = {
                accountUUID,
                entryUUID
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public void insert(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        var sql = "INSERT INTO t_entry_role" +
                "(account_uuid, entry_uuid)" +
                "VALUES" +
                "(?, ?)";

        Object[] args = {
                accountUUID,
                entryUUID
        };

        this.jvarJdbc.update(sql, args);
    }

    public void deleteEntry(final UUID entryUUID) {
        var sql = "DELETE FROM t_entry_role WHERE entry_uuid = ?;";
        Object[] args = {
                entryUUID
        };

        this.jvarJdbc.update(sql, args);
    }

    private EntryRoleEntity getEntry(final Map<String, Object> row) {
        return new EntryRoleEntity(
                (UUID)row.get("account_uuid"),
                (UUID)row.get("entry_uuid")
        );
    }
}
