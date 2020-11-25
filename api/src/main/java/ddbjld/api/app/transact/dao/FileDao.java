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
        var sql = "SELECT * FROM t_file WHERE uuid = ?;";
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
        var sql = "SELECT * FROM t_file WHERE entry_uuid = ?;";
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
    public boolean hasWorkBook(final UUID entryUUID) {
        var sql = "SELECT * FROM t_file WHERE entry_uuid = ? AND active = true;";
        Object[] args = {
                entryUUID
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }


    private FileEntity getEntity(final Map<String, Object> row) {
        return new FileEntity(
                (UUID)row.get("uuid"),
                (UUID)row.get("entry_uuid"),
                (String)row.get("name"),
                (String)row.get("type"),
                (Boolean) row.get("active"),
                (Integer) row.get("revision"),
                (UUID)row.get("validation_uuid"),
                (String)row.get("validation_status"),
                ((Timestamp) row.get("uploaded_at")).toLocalDateTime()
        );
    }
}
