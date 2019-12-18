package com.ddbj.ld.dao;

import com.ddbj.ld.common.dao.IntermediateDaoInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class SampleExperimentDao implements IntermediateDaoInterface {
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int insert(String sampleAccession, String experimentAccession) {
        int result = 0;

        try {
            result = jdbcTemplate.update("insert into sample_experiment(sample_accession, experiment_accession) values(?, ?)", sampleAccession, experimentAccession);
        } catch(Exception e) {
            log.debug(e.getMessage());
        } finally {
            return result;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int[] bulkInsert(List<Object[]> accessionList) {
        int[] argTypes = new int[2];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        int[] results = new int[accessionList.size()];

        try {
            results = jdbcTemplate.batchUpdate(
                    "insert into sample_experiment(sample_accession, experiment_accession) values(?, ?)",
                    accessionList, argTypes);
        } catch(Exception e) {
            log.debug(e.getMessage());
        } finally {
            return results;
        }
    }
}
