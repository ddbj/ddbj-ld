package com.ddbj.ld.app.transact.dao.primary.bioproject;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.data.beans.common.BioLiveListBean;
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
public class DDBJBioProjectDao {

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

        var sql = "INSERT INTO t_ddbj_bioproject (" +
                "accession, status, visibility, date_created, date_published, date_modified, json, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_ddbj_bioproject is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_ddbj_bioproject");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_ddbj_bioproject_01 ON t_ddbj_bioproject (accession);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_ddbj_bioproject_01;");
    }

    public void createTempTable(final String date) {
        var tableName = "t_ddbj_bioproject_" + date;

        var sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(" +
                "  accession      varchar(14) NOT NULL," +
                "  status         text        NOT NULL," +
                "  visibility     text        NOT NULL," +
                "  date_created   timestamp           ," +
                "  date_published timestamp           ," +
                "  date_modified  timestamp           ," +
                "  json  text   NOT NULL," +
                "  created_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "  updated_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "  PRIMARY KEY (accession)" +
                ");";

        this.jdbc.update(sql);
    }

    public void createTempIndex(final String date) {
        this.jdbc.update("CREATE INDEX idx_ddbj_bioproject_01_" + date + " ON t_ddbj_bioproject_" + date +  " (accession);");
    }

    public void drop() {
        this.jdbc.update("DROP TABLE IF EXISTS t_ddbj_bioproject;");
    }

    public void dropTempTable(final String date) {
        this.jdbc.update("DROP TABLE IF EXISTS t_ddbj_bioproject_" + date + ";");
    }

    public void rename(final String date) {
        this.jdbc.update("ALTER TABLE t_ddbj_bioproject_" + date + " RENAME TO t_ddbj_bioproject;");
    }

    public void renameIndex(final String date) {
        this.jdbc.update("ALTER INDEX idx_ddbj_bioproject_01_" + date + " RENAME TO idx_ddbj_bioproject;");
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

        var sql = "INSERT INTO t_ddbj_bioproject_" + date + " (" +
                "accession, status, visibility, date_created, date_published, date_modified, json, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_ddbj_bioproject is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    @Transactional(readOnly=true)
    public BioLiveListBean select(final String accession) {
        var sql = "SELECT * FROM t_ddbj_bioproject " +
                "WHERE accession = ? " +
                "AND date_published IS NOT NULL " +
                "ORDER BY accession;";

        Object[] args = {
                accession
        };

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getBioLiveList(rs), args);

        return resultList.size() > 0 ? resultList.get(0) : null;
    }

    @Transactional(readOnly=true)
    public List<BioLiveListBean> selNewRecord(final String date) {
        var sql = "SELECT a.* " +
                "FROM t_ddbj_bioproject_" + date +" a " +
                "         LEFT OUTER JOIN t_ddbj_bioproject b ON a.accession = b.accession " +
                "WHERE b.accession IS NULL;";

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getBioLiveList(rs));

        return resultList;
    }

    @Transactional(readOnly=true)
    public List<BioLiveListBean> selToUnpublished(final String date) {
        var sql = "SELECT a.* " +
                "FROM t_ddbj_bioproject a " +
                "         LEFT OUTER JOIN t_ddbj_bioproject_" + date + " b ON a.accession = b.accession " +
                "WHERE b.accession IS NULL;";

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getBioLiveList(rs));

        return resultList;
    }

    @Transactional(readOnly=true)
    public List<BioLiveListBean> selUpdatedRecord(final String date) {
        var sql = "SELECT a.* " +
                "FROM t_ddbj_bioproject_" + date +" a " +
                "         INNER JOIN t_ddbj_bioproject b ON a.accession = b.accession " +
                "WHERE a.status = 'public' " +
                "  AND a.json != b.json";

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getBioLiveList(rs));

        return resultList;
    }
}
