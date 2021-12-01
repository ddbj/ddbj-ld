package com.ddbj.ld.app.transact.dao.primary.jga;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.common.constants.TypeEnum;
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
public class JGAExperimentStudyDao implements JGADao {

    private final JdbcTemplate jdbc;
    private final JsonModule jsonModule;

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

    public List<DBXrefsBean> selDataSet(
            final String studyAccession
    ) {
        var sql = "SELECT DISTINCT " +
                "    tjdd.dataset_accession AS accession " +
                "FROM " +
                "    t_jga_experiment_study tjes " +
                "        INNER JOIN " +
                "    t_jga_data_experiment tjde " +
                "    ON  tjes.experiment_accession = tjde.experiment_accession " +
                "        INNER JOIN " +
                "    t_jga_dataset_data tjdd " +
                "    ON  tjde.data_accession = tjdd.data_accession " +
                "WHERE" +
                "    tjes.study_accession = ? " +
                "ORDER BY " +
                "    accession;";

        Object[] args = {
                studyAccession
        };

        this.jdbc.setFetchSize(1000);

        return this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.JGA_DATASET.type), args);
    }

    public List<DBXrefsBean> selPolicy(
            final String studyAccession
    ) {
        var sql = "SELECT DISTINCT " +
                "    tjdp.policy_accession AS accession " +
                "FROM " +
                "    t_jga_experiment_study tjes " +
                "        INNER JOIN " +
                "    t_jga_data_experiment tjde " +
                "    ON  tjes.experiment_accession = tjde.experiment_accession " +
                "        INNER JOIN " +
                "    t_jga_dataset_data tjdd " +
                "    ON  tjde.data_accession = tjdd.data_accession " +
                "        INNER JOIN " +
                "    t_jga_dataset_policy tjdp " +
                "    ON tjdd.dataset_accession = tjdp.dataset_accession " +
                "WHERE " +
                "        tjes.study_accession = ? " +
                "ORDER BY " +
                "    accession;";

        Object[] args = {
                studyAccession
        };

        this.jdbc.setFetchSize(1000);

        return this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.JGA_POLICY.type), args);
    }

    public List<DBXrefsBean> selAllStudy() {
        var sql = "SELECT  " +
                "    DISTINCT study_accession AS accession  " +
                "FROM  " +
                "    t_jga_experiment_study  " +
                "UNION  " +
                "SELECT DISTINCT  " +
                "    study_accession AS accession  " +
                "FROM  " +
                "    t_jga_analysis_study  " +
                "ORDER BY accession;";

        this.jdbc.setFetchSize(1000);

        return this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.JGA_STUDY.type));
    }
}
