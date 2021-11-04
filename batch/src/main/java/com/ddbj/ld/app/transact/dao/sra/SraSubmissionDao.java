package com.ddbj.ld.app.transact.dao.sra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.data.beans.common.AccessionsBean;
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
public class SraSubmissionDao {

    private final JdbcTemplate jdbc;

    private final JsonModule jsonModule;

    public void bulkInsert(final List<Object[]> recordList) {

        int[] argTypes = new int[20];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.VARCHAR;
        argTypes[3] = Types.TIMESTAMP;
        argTypes[4] = Types.TIMESTAMP;
        argTypes[5] = Types.TIMESTAMP;
        argTypes[6] = Types.VARCHAR;
        argTypes[7] = Types.VARCHAR;
        argTypes[8] = Types.VARCHAR;
        argTypes[9] = Types.VARCHAR;
        argTypes[10] = Types.VARCHAR;
        argTypes[11] = Types.VARCHAR;
        argTypes[12] = Types.VARCHAR;
        argTypes[13] = Types.INTEGER;
        argTypes[14] = Types.VARCHAR;
        argTypes[15] = Types.VARCHAR;
        argTypes[16] = Types.VARCHAR;
        argTypes[17] = Types.VARCHAR;
        argTypes[18] = Types.VARCHAR;
        argTypes[19] = Types.VARCHAR;

        var sql = "INSERT INTO t_sra_submission (" +
                "accession, submission, status, updated, published, received, type, center, visibility, alias, experiment, sample, study, loaded, spots, bases, md5sum, biosample, bioproject, replacedby, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_sra_submission is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_sra_submission");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_sra_submission_01 ON t_sra_submission (accession);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_02 ON t_sra_submission (submission);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_03 ON t_sra_submission (status);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_04 ON t_sra_submission (updated);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_05 ON t_sra_submission (visibility);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_06 ON t_sra_submission (experiment);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_07 ON t_sra_submission (sample);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_08 ON t_sra_submission (study);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_09 ON t_sra_submission (biosample);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_10 ON t_sra_submission (bioproject);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_02;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_03;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_04;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_05;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_06;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_07;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_08;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_09;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_submission_10;");
    }

    public void createTempTable(final String date) {
        var tableName = "t_sra_submission_" + date;

        var sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(" +
                "    accession  varchar(14) NOT NULL," +
                "    submission varchar(14) NOT NULL," +
                "    status     varchar(11) NOT NULL," +
                "    updated    timestamp  ," +
                "    published  timestamp  ," +
                "    received   timestamp  ," +
                "    type       varchar(10) NOT NULL," +
                "    center     text       ," +
                "    visibility varchar(17) NOT NULL," +
                "    alias      text       ," +
                "    experiment varchar(14)," +
                "    sample     varchar(14)," +
                "    study      varchar(14)," +
                "    loaded     smallint   ," +
                "    spots      text       ," +
                "    bases      text       ," +
                "    md5sum     varchar(32)," +
                "    biosample  varchar(14)," +
                "    bioproject varchar(14)," +
                "    replacedby text       ," +
                "    created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "    updated_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "    PRIMARY KEY (accession)" +
                ");";

        this.jdbc.update(sql);
    }

    public void createTempIndex(final String date) {
        this.jdbc.update("CREATE INDEX idx_sra_submission_01_" + date + " ON t_sra_submission_" + date +  " (accession);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_02_" + date + " ON t_sra_submission_" + date +  " (submission);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_03_" + date + " ON t_sra_submission_" + date +  " (status);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_04_" + date + " ON t_sra_submission_" + date +  " (updated);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_05_" + date + " ON t_sra_submission_" + date +  " (visibility);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_06_" + date + " ON t_sra_submission_" + date +  " (experiment);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_07_" + date + " ON t_sra_submission_" + date +  " (sample);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_08_" + date + " ON t_sra_submission_" + date +  " (study);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_09_" + date + " ON t_sra_submission_" + date +  " (biosample);");
        this.jdbc.update("CREATE INDEX idx_sra_submission_10_" + date + " ON t_sra_submission_" + date +  " (bioproject);");
    }

    public void drop() {
        this.jdbc.update("DROP TABLE IF EXISTS t_sra_submission;");
    }

    public void rename(final String date) {
        this.jdbc.update("ALTER TABLE t_sra_submission_" + date + " RENAME TO t_sra_submission;");
    }

    public void bulkInsertTemp(
            final String date,
            final List<Object[]> recordList
    ) {

        int[] argTypes = new int[20];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.VARCHAR;
        argTypes[3] = Types.TIMESTAMP;
        argTypes[4] = Types.TIMESTAMP;
        argTypes[5] = Types.TIMESTAMP;
        argTypes[6] = Types.VARCHAR;
        argTypes[7] = Types.VARCHAR;
        argTypes[8] = Types.VARCHAR;
        argTypes[9] = Types.VARCHAR;
        argTypes[10] = Types.VARCHAR;
        argTypes[11] = Types.VARCHAR;
        argTypes[12] = Types.VARCHAR;
        argTypes[13] = Types.INTEGER;
        argTypes[14] = Types.VARCHAR;
        argTypes[15] = Types.VARCHAR;
        argTypes[16] = Types.VARCHAR;
        argTypes[17] = Types.VARCHAR;
        argTypes[18] = Types.VARCHAR;
        argTypes[19] = Types.VARCHAR;

        var sql = "INSERT INTO t_sra_submission_" + date + " (" +
                "accession, submission, status, updated, published, received, type, center, visibility, alias, experiment, sample, study, loaded, spots, bases, md5sum, biosample, bioproject, replacedby, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_sra_submission is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    @Transactional(readOnly=true)
    public AccessionsBean select(final String accession) {
        var sql = "SELECT * FROM t_sra_submission " +
                "WHERE accession = ? " +
                "AND published IS NOT NULL " +
                "ORDER BY accession;";

        Object[] args = {
                accession
        };

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getAccessions(rs), args);

        return resultList.size() > 0 ? resultList.get(0) : null;
    }
}
