package com.ddbj.ld.app.transact.dao.jga;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@AllArgsConstructor
@Slf4j
public class JgaDateDao {

    private JdbcTemplate jdbc;

    public void bulkInsert(final List<Object[]> recordList) {

        var argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.TIMESTAMP;
        argTypes[2] = Types.TIMESTAMP;
        argTypes[3] = Types.TIMESTAMP;

        var sql = "INSERT INTO t_jga_date (accession, date_created, date_published, date_modified, created_at, updated_at) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {
            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_jga_date is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public Map<String, Object> selJgaDate(final String accession) {
        var sql = "SELECT * FROM t_jga_date " +
                  "WHERE accession = ? " +
                  // FIXME 不要な条件？
                  "AND date_published IS NOT NULL";

        this.jdbc.setFetchSize(1000);

        Object[] args = {
                accession
        };

        return this.jdbc.queryForMap(sql, args);
    }

    public void deleteAll() {
        var sql = "DELETE FROM t_jga_date";

        this.jdbc.update(sql);
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_jga_date_01 ON t_jga_date (accession);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX idx_jga_date_01;");
    }
}
