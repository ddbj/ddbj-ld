package com.ddbj.ld.app.transact.dao.dra;

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
public class DraAnalysisDao {

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

        var sql = "INSERT INTO t_dra_analysis (" +
                "accession, submission, status, updated, published, received, type, center, visibility, alias, experiment, sample, study, loaded, spots, bases, md5sum, biosample, bioproject, replacedby, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_dra_analysis is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_dra_analysis");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_dra_analysis_01 ON t_dra_analysis (accession);");
        this.jdbc.update("CREATE INDEX idx_dra_analysis_02 ON t_dra_analysis (submission);");
        this.jdbc.update("CREATE INDEX idx_dra_analysis_03 ON t_dra_analysis (status);");
        this.jdbc.update("CREATE INDEX idx_dra_analysis_04 ON t_dra_analysis (updated);");
        this.jdbc.update("CREATE INDEX idx_dra_analysis_05 ON t_dra_analysis (visibility);");
        this.jdbc.update("CREATE INDEX idx_dra_analysis_06 ON t_dra_analysis (experiment);");
        this.jdbc.update("CREATE INDEX idx_dra_analysis_07 ON t_dra_analysis (sample);");
        this.jdbc.update("CREATE INDEX idx_dra_analysis_08 ON t_dra_analysis (study);");
        this.jdbc.update("CREATE INDEX idx_dra_analysis_09 ON t_dra_analysis (biosample);");
        this.jdbc.update("CREATE INDEX idx_dra_analysis_10 ON t_dra_analysis (bioproject);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_02;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_03;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_04;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_05;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_06;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_07;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_08;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_09;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_analysis_10;");
    }

    @Transactional(readOnly=true)
    public List<DBXrefsBean> selByBioProject(final String bioProjectAccession) {
        var sql = "SELECT accession FROM t_dra_analysis " +
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
        var sql = "SELECT accession FROM t_dra_analysis " +
                "WHERE submission = ? " +
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
        var sql = "SELECT accession FROM t_dra_analysis " +
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
        var sql = "SELECT * FROM t_dra_analysis " +
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
