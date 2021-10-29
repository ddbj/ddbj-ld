package com.ddbj.ld.app.transact.dao.biosample;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.data.beans.common.LiveListBean;
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
public class BioSampleDao {

    private final JdbcTemplate jdbc;

    private final JsonModule jsonModule;

    public void bulkInsert(final List<Object[]> recordList) {

        int[] argTypes = new int[7];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.VARCHAR;
        argTypes[3] = Types.TIMESTAMP;
        argTypes[4] = Types.TIMESTAMP;
        argTypes[5] = Types.TIMESTAMP;
        argTypes[6] = Types.VARCHAR;

        var sql = "INSERT INTO t_biosample (" +
                "accession, status, visibility, date_created, date_published, date_modified, json, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_biosample is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_biosample");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_biosample_01 ON t_biosample (accession);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_biosample_01;");
    }

    public void createTempTable(final String date) {
        var tableName = "t_biosample_" + date;

        var sql = "CREATE TABLE " + tableName +
                "(" +
                "  accession      varchar(14) NOT NULL," +
                "  status         text        NOT NULL," +
                "  visibility     text        NOT NULL," +
                "  date_created   timestamp   NOT NULL," +
                "  date_published timestamp   NOT NULL," +
                "  date_modified  timestamp   NOT NULL," +
                "  created_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "  updated_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "  PRIMARY KEY (accession)" +
                ");";

        this.jdbc.update(sql);
    }

    public void createTempIndex(final String date) {
        this.jdbc.update("CREATE INDEX idx_biosample_01_" + date + " ON t_biosample_" + date +  " (accession);");
    }

    public void drop() {
        this.jdbc.update("DROP TABLE IF EXISTS t_biosample;");
    }

    public void dropTempTable(final String date) {
        this.jdbc.update("DROP TABLE IF EXISTS t_biosample_" + date + ";");
    }

    public void rename(final String date) {
        this.jdbc.update("ALTER TABLE t_biosample_" + date + " RENAME TO t_biosample;");
    }

    public void bulkInsertTemp(
            final String date,
            final List<Object[]> recordList
    ) {

        int[] argTypes = new int[7];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.VARCHAR;
        argTypes[3] = Types.TIMESTAMP;
        argTypes[4] = Types.TIMESTAMP;
        argTypes[5] = Types.TIMESTAMP;
        argTypes[6] = Types.VARCHAR;

        var sql = "INSERT INTO t_biosample_" + date + " (" +
                "accession, status, visibility, date_created, date_published, date_modified, json, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_biosample is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    @Transactional(readOnly=true)
    public LiveListBean select(final String accession) {
        var sql = "SELECT * FROM t_biosample " +
                "WHERE accession = ? " +
                "AND published IS NOT NULL " +
                "ORDER BY accession;";

        Object[] args = {
                accession
        };

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getLiveList(rs), args);

        return resultList.size() > 0 ? resultList.get(0) : null;
    }
}
