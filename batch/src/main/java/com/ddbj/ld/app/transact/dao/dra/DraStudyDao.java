package com.ddbj.ld.app.transact.dao.dra;

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
public class DraStudyDao {

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

        var sql = "INSERT INTO t_dra_study (" +
                "accession, submission, status, updated, published, received, type, center, visibility, alias, experiment, sample, study, loaded, spots, bases, md5sum, biosample, bioproject, replacedby, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_dra_study is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_dra_study");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_dra_study_01 ON t_dra_study (accession);");
        this.jdbc.update("CREATE INDEX idx_dra_study_02 ON t_dra_study (submission);");
        this.jdbc.update("CREATE INDEX idx_dra_study_03 ON t_dra_study (status);");
        this.jdbc.update("CREATE INDEX idx_dra_study_04 ON t_dra_study (updated);");
        this.jdbc.update("CREATE INDEX idx_dra_study_05 ON t_dra_study (visibility);");
        this.jdbc.update("CREATE INDEX idx_dra_study_06 ON t_dra_study (experiment);");
        this.jdbc.update("CREATE INDEX idx_dra_study_07 ON t_dra_study (sample);");
        this.jdbc.update("CREATE INDEX idx_dra_study_08 ON t_dra_study (study);");
        this.jdbc.update("CREATE INDEX idx_dra_study_09 ON t_dra_study (biosample);");
        this.jdbc.update("CREATE INDEX idx_dra_study_10 ON t_dra_study (bioproject);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_02;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_03;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_04;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_05;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_06;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_07;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_08;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_09;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_study_10;");
    }

    @Transactional(readOnly=true)
    public AccessionsBean select(final String accession) {
        var sql = "SELECT * FROM t_dra_study " +
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
