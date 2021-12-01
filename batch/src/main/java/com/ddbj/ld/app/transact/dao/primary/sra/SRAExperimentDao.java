package com.ddbj.ld.app.transact.dao.primary.sra;

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
public class SRAExperimentDao {

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

        var sql = "INSERT INTO t_sra_experiment (" +
                "accession, submission, status, updated, published, received, type, center, visibility, alias, experiment, sample, study, loaded, spots, bases, md5sum, biosample, bioproject, replacedby, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_sra_experiment is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_sra_experiment");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_sra_experiment_01 ON t_sra_experiment (accession);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_02 ON t_sra_experiment (submission);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_03 ON t_sra_experiment (status);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_04 ON t_sra_experiment (updated);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_05 ON t_sra_experiment (visibility);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_06 ON t_sra_experiment (experiment);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_07 ON t_sra_experiment (sample);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_08 ON t_sra_experiment (study);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_09 ON t_sra_experiment (biosample);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_10 ON t_sra_experiment (bioproject);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_02;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_03;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_04;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_05;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_06;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_07;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_08;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_09;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_experiment_10;");
    }

    public void createTempTable(final String date) {
        var tableName = "t_sra_experiment_" + date;

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
                "    visibility text        NOT NULL," +
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
        this.jdbc.update("CREATE INDEX idx_sra_experiment_01_" + date + " ON t_sra_experiment_" + date +  " (accession);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_02_" + date + " ON t_sra_experiment_" + date +  " (submission);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_03_" + date + " ON t_sra_experiment_" + date +  " (status);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_04_" + date + " ON t_sra_experiment_" + date +  " (updated);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_05_" + date + " ON t_sra_experiment_" + date +  " (visibility);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_06_" + date + " ON t_sra_experiment_" + date +  " (experiment);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_07_" + date + " ON t_sra_experiment_" + date +  " (sample);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_08_" + date + " ON t_sra_experiment_" + date +  " (study);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_09_" + date + " ON t_sra_experiment_" + date +  " (biosample);");
        this.jdbc.update("CREATE INDEX idx_sra_experiment_10_" + date + " ON t_sra_experiment_" + date +  " (bioproject);");
    }

    public void drop() {
        this.jdbc.update("DROP TABLE IF EXISTS t_sra_experiment;");
    }

    public void rename(final String date) {
        this.jdbc.update("ALTER TABLE t_sra_experiment_" + date + " RENAME TO t_sra_experiment;");
    }

    public void renameIndex(final String date) {
        this.jdbc.update("ALTER INDEX idx_sra_experiment_01_" + date + " RENAME TO idx_sra_experiment_01;");
        this.jdbc.update("ALTER INDEX idx_sra_experiment_02_" + date + " RENAME TO idx_sra_experiment_02;");
        this.jdbc.update("ALTER INDEX idx_sra_experiment_03_" + date + " RENAME TO idx_sra_experiment_03;");
        this.jdbc.update("ALTER INDEX idx_sra_experiment_04_" + date + " RENAME TO idx_sra_experiment_04;");
        this.jdbc.update("ALTER INDEX idx_sra_experiment_05_" + date + " RENAME TO idx_sra_experiment_05;");
        this.jdbc.update("ALTER INDEX idx_sra_experiment_06_" + date + " RENAME TO idx_sra_experiment_06;");
        this.jdbc.update("ALTER INDEX idx_sra_experiment_07_" + date + " RENAME TO idx_sra_experiment_07;");
        this.jdbc.update("ALTER INDEX idx_sra_experiment_08_" + date + " RENAME TO idx_sra_experiment_08;");
        this.jdbc.update("ALTER INDEX idx_sra_experiment_09_" + date + " RENAME TO idx_sra_experiment_09;");
        this.jdbc.update("ALTER INDEX idx_sra_experiment_10_" + date + " RENAME TO idx_sra_experiment_10;");
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

        var sql = "INSERT INTO t_sra_experiment_" + date + " (" +
                "accession, submission, status, updated, published, received, type, center, visibility, alias, experiment, sample, study, loaded, spots, bases, md5sum, biosample, bioproject, replacedby, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_sra_experiment is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    @Transactional(readOnly=true)
    public AccessionsBean select(final String accession) {
        var sql = "SELECT * FROM t_sra_experiment " +
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

    @Transactional(readOnly=true)
    public List<AccessionsBean> selToSuppressed(final String date) {
        var sql = "SELECT a.* " +
                "FROM t_sra_experiment_" + date +" a " +
                "         INNER JOIN t_sra_experiment b ON a.accession = b.accession " +
                "WHERE a.status = 'suppressed' " +
                "  AND b.status = 'public';";

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getAccessions(rs));

        return resultList;
    }

    @Transactional(readOnly=true)
    public List<AccessionsBean> selToUnpublished(final String date) {
        var sql = "SELECT a.* " +
                "FROM t_sra_experiment_" + date +" a " +
                "         INNER JOIN t_sra_experiment b ON a.accession = b.accession " +
                "WHERE a.status = 'unpublished' " +
                "  AND b.status = 'public';";

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getAccessions(rs));

        return resultList;
    }

    @Transactional(readOnly=true)
    public List<AccessionsBean> selSuppressedToPublic(final String date) {
        var sql = "SELECT a.* " +
                "FROM t_sra_experiment_" + date +" a " +
                "         INNER JOIN t_sra_experiment b ON a.accession = b.accession " +
                "WHERE a.status = 'public' " +
                "  AND b.status = 'suppressed';";

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getAccessions(rs));

        return resultList;
    }

    @Transactional(readOnly=true)
    public List<AccessionsBean> selSuppressedToUnpublished(final String date) {
        var sql = "SELECT a.* " +
                "FROM t_sra_experiment_" + date +" a " +
                "         INNER JOIN t_sra_experiment b ON a.accession = b.accession " +
                "WHERE a.status = 'unpublished' " +
                "  AND b.status = 'suppressed';";

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getAccessions(rs));

        return resultList;
    }
}
