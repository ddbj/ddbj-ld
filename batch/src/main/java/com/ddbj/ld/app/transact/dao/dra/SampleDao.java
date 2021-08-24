package com.ddbj.ld.app.transact.dao.dra;

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
public class SampleDao {

    private final JdbcTemplate jdbc;

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

        var sql = "INSERT INTO t_dra_sample (" +
                "accession, submission, status, updated, published, received, type, center, visibility, alias, experiment, sample, study, loaded, spots, bases, md5sum, biosample, bioproject, replacedby, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_dra_sample is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_dra_sample");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_dra_sample_01 ON t_dra_sample (accession);");
        this.jdbc.update("CREATE INDEX idx_dra_sample_02 ON t_dra_sample (submission);");
        this.jdbc.update("CREATE INDEX idx_dra_sample_03 ON t_dra_sample (status);");
        this.jdbc.update("CREATE INDEX idx_dra_sample_04 ON t_dra_sample (updated);");
        this.jdbc.update("CREATE INDEX idx_dra_sample_05 ON t_dra_sample (visibility);");
        this.jdbc.update("CREATE INDEX idx_dra_sample_06 ON t_dra_sample (experiment);");
        this.jdbc.update("CREATE INDEX idx_dra_sample_07 ON t_dra_sample (sample);");
        this.jdbc.update("CREATE INDEX idx_dra_sample_08 ON t_dra_sample (study);");
        this.jdbc.update("CREATE INDEX idx_dra_sample_09 ON t_dra_sample (biosample);");
        this.jdbc.update("CREATE INDEX idx_dra_sample_10 ON t_dra_sample (bioproject);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_02;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_03;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_04;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_05;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_06;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_07;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_08;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_09;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_sample_10;");
    }
}
