package com.ddbj.ld.app.transact.dao.jga;

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
public class ExperimentStudyDao implements JgaDao {

    private final JdbcTemplate jdbc;

    @Override
    public void bulkInsert(final List<Object[]> recordList) {
        var argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;

        var sql = "INSERT INTO t_jga_experiment_study (experiment_accession, study_accession, created_at, updated_at) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {
            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_jga_experiment_study is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    @Override
    public void deleteAll() {
        this.jdbc.update("DELETE FROM t_jga_experiment_study");
    }

    @Override
    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_jga_experiment_study_01 ON t_jga_experiment_study (experiment_accession);");
        this.jdbc.update("CREATE INDEX idx_jga_experiment_study_02 ON t_jga_experiment_study (study_accession);");
    }

    @Override
    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_jga_experiment_study_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_jga_experiment_study_02;");
    }
}
