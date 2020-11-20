package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.CommentEntity;
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

        return this.getEntry(row);
    }

    @Transactional(readOnly = true)
    public List<CommentEntity> readEntryFiles(final UUID entryUUID) {
        var sql = "SELECT * FROM t_comment WHERE entry_uuid = ?;";
        Object[] args = {
                entryUUID,
        };

        var rows = SpringJdbcUtil.MapQuery.all(this.jvarJdbc, sql, args);

        if(null == rows) {
            return null;
        }

        var entities = new ArrayList<CommentEntity>();

        for(var row: rows) {
            var entity = this.getEntry(row);

            entities.add(entity);
        }

        return entities;
    }

    private CommentEntity getEntry(final Map<String, Object> row) {
        return new CommentEntity(
                (UUID)row.get("uuid"),
                (UUID)row.get("entry_uuid"),
                (UUID)row.get("account_uuid"),
                (String)row.get("comment"),
                ((Timestamp) row.get("created_at")).toLocalDateTime(),
                ((Timestamp) row.get("updated_at")).toLocalDateTime()
        );
    }
}
