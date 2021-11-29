package com.ddbj.ld.app.transact.dao.jga;

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
public class JGADataSetDataDao implements JGADao {

    private final JdbcTemplate jdbc;
    private final JsonModule jsonModule;

    @Override
    public void bulkInsert(final List<Object[]> recordList) {
        var argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;

        var sql = "INSERT INTO t_jga_dataset_data (dataset_accession, data_accession, created_at, updated_at) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {
            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_jga_dataset_data is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    @Override
    public void deleteAll() {
        this.jdbc.update("DELETE FROM t_jga_dataset_data");
    }

    @Override
    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_jga_dataset_data_01 ON t_jga_dataset_data (dataset_accession);");
        this.jdbc.update("CREATE INDEX idx_jga_dataset_data_02 ON t_jga_dataset_data (data_accession);");
    }

    @Override
    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_jga_dataset_data_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_jga_dataset_data_02;");
    }

    public List<DBXrefsBean> selStudy(
            final String dataSetAccession
    ) {
        var sql = "SELECT DISTINCT   " +
                "  tjes.study_accession AS accession " +
                "FROM " +
                "  t_jga_dataset_data tjdd " +
                "      INNER JOIN " +
                "  t_jga_data_experiment tjde " +
                "  ON  tjdd.data_accession = tjde.data_accession " +
                "      INNER JOIN " +
                "  t_jga_experiment_study tjes " +
                "  ON  tjde.experiment_accession = tjes.experiment_accession " +
                "WHERE " +
                "      tjdd.dataset_accession = ? " +
                "UNION " +
                "SELECT DISTINCT " +
                "    tjas.study_accession AS accession " +
                "FROM " +
                "    t_jga_dataset_analysis tjda " +
                "        INNER JOIN " +
                "    t_jga_analysis_study tjas " +
                "    ON  tjda.analysis_accession = tjas.analysis_accession " +
                "WHERE " +
                "        tjda.dataset_accession = ? " +
                "ORDER BY accession;";

        Object[] args = {
                dataSetAccession,
                dataSetAccession
        };

        this.jdbc.setFetchSize(1000);

        return this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.JGA_STUDY.type), args);
    }

    public List<DBXrefsBean> selAllDataSet() {
        var sql = "SELECT DISTINCT " +
                "    dataset_accession AS accession " +
                "FROM " +
                "    t_jga_dataset_data " +
                "UNION " +
                "SELECT " +
                "    DISTINCT dataset_accession " +
                "FROM " +
                "    t_jga_dataset_analysis " +
                "ORDER BY " +
                "    accession;";

        this.jdbc.setFetchSize(1000);

        return this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.JGA_DATASET.type));
    }
}
