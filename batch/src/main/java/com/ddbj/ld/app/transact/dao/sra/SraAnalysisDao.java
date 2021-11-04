package com.ddbj.ld.app.transact.dao.sra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.data.beans.common.AccessionsBean;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
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
public class SraAnalysisDao {

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

        var sql = "INSERT INTO t_sra_analysis (" +
                "accession, submission, status, updated, published, received, type, center, visibility, alias, experiment, sample, study, loaded, spots, bases, md5sum, biosample, bioproject, replacedby, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_sra_analysis is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_sra_analysis");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_sra_analysis_01 ON t_sra_analysis (accession);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_02 ON t_sra_analysis (submission);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_03 ON t_sra_analysis (status);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_04 ON t_sra_analysis (updated);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_05 ON t_sra_analysis (visibility);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_06 ON t_sra_analysis (experiment);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_07 ON t_sra_analysis (sample);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_08 ON t_sra_analysis (study);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_09 ON t_sra_analysis (biosample);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_10 ON t_sra_analysis (bioproject);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_02;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_03;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_04;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_05;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_06;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_07;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_08;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_09;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_sra_analysis_10;");
    }

    public void createTempTable(final String date) {
        var tableName = "t_sra_analysis_" + date;

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
        this.jdbc.update("CREATE INDEX idx_sra_analysis_01_" + date + " ON t_sra_analysis_" + date +  " (accession);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_02_" + date + " ON t_sra_analysis_" + date +  " (submission);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_03_" + date + " ON t_sra_analysis_" + date +  " (status);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_04_" + date + " ON t_sra_analysis_" + date +  " (updated);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_05_" + date + " ON t_sra_analysis_" + date +  " (visibility);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_06_" + date + " ON t_sra_analysis_" + date +  " (experiment);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_07_" + date + " ON t_sra_analysis_" + date +  " (sample);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_08_" + date + " ON t_sra_analysis_" + date +  " (study);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_09_" + date + " ON t_sra_analysis_" + date +  " (biosample);");
        this.jdbc.update("CREATE INDEX idx_sra_analysis_10_" + date + " ON t_sra_analysis_" + date +  " (bioproject);");
    }

    public void drop() {
        this.jdbc.update("DROP TABLE IF EXISTS t_sra_analysis;");
    }

    public void rename(final String date) {
        this.jdbc.update("ALTER TABLE t_sra_analysis_" + date + " RENAME TO t_sra_analysis;");
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

        var sql = "INSERT INTO t_sra_analysis_" + date + " (" +
                "accession, submission, status, updated, published, received, type, center, visibility, alias, experiment, sample, study, loaded, spots, bases, md5sum, biosample, bioproject, replacedby, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_sra_analysis is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    @Transactional(readOnly=true)
    public List<DBXrefsBean> selByBioProject(final String bioProjectAccession) {
        var sql = "SELECT accession FROM t_sra_analysis " +
                "WHERE bioproject = ? " +
                "AND published IS NOT NULL " +
                "ORDER BY accession;";

        Object[] args = {
                bioProjectAccession
        };

        this.jdbc.setFetchSize(1000);

        return this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.ANALYSIS.type), args);
    }

    @Transactional(readOnly=true)
    public List<DBXrefsBean> selBySubmission(final String submissionAccession) {
        var sql = "SELECT accession FROM t_sra_analysis " +
                "WHERE submission = ? " +
                // TODO このあたりは変えるべき？ status = 'live'といったようにしたほうがいいかも
                "AND published IS NOT NULL " +
                "ORDER BY accession;";

        Object[] args = {
                submissionAccession
        };

        this.jdbc.setFetchSize(1000);

        return this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.ANALYSIS.type), args);
    }

    @Transactional(readOnly=true)
    public List<DBXrefsBean> selByStudy(final String studyAccession) {
        var sql = "SELECT accession FROM t_sra_analysis " +
                "WHERE study = ? " +
                "AND published IS NOT NULL " +
                "ORDER BY accession;";

        Object[] args = {
                studyAccession
        };

        this.jdbc.setFetchSize(1000);

        return this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.ANALYSIS.type), args);
    }

    @Transactional(readOnly=true)
    public AccessionsBean select(final String accession) {
        var sql = "SELECT * FROM t_sra_analysis " +
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
