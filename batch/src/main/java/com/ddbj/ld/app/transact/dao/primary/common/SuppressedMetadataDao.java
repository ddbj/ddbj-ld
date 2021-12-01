package com.ddbj.ld.app.transact.dao.primary.common;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@AllArgsConstructor
@Slf4j
public class SuppressedMetadataDao {

    private final JdbcTemplate jdbc;

    public void bulkInsert(final List<Object[]> recordList) {
        var argTypes = new int[3];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.VARCHAR;

        var sql = "INSERT INTO t_suppressed_metadata (accession, type, json, created_at, updated_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {
            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_suppressed_metadata is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_suppressed_metadata_01 ON t_suppressed_metadata (accession);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_suppressed_metadata_01;");
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_suppressed_metadata;");
    }

    public void bulkDelete(final List<Object[]> args) {
        int[] types = { Types.VARCHAR };

        var sql = "DELETE FROM t_suppressed_metadata " +
                "WHERE accession = ?; ";

        this.jdbc.batchUpdate(sql, args, types);
    }
}
