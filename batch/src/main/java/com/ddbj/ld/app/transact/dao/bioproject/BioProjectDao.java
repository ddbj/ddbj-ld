package com.ddbj.ld.app.transact.dao.bioproject;

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
public class BioProjectDao {

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

        var sql = "INSERT INTO t_bioproject (" +
                "accession, status, visibility, date_created, date_published, date_modified, json, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_bioproject is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_bioproject");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_bioproject_01 ON t_bioproject (accession);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_bioproject_01;");
    }

    public void createTempTable(final String date) {
        var tableName = "t_bioproject_" + date;

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
        this.jdbc.update("CREATE INDEX idx_bioproject_01_" + date + " ON t_bioproject_" + date +  " (accession);");
    }

    public void drop() {
        this.jdbc.update("DROP TABLE IF EXISTS t_bioproject;");
    }

    public void rename(final String date) {
        this.jdbc.update("ALTER TABLE t_bioproject_" + date + " RENAME TO t_bioproject;");
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

        var sql = "INSERT INTO t_bioproject_" + date + " (" +
                "accession, status, visibility, date_created, date_published, date_modified, json, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_bioproject is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    @Transactional(readOnly=true)
    public LiveListBean select(final String accession) {
        var sql = "SELECT * FROM t_bioproject " +
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
