package com.ddbj.ld.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class Experiment {
    private JdbcTemplate jdbcTemplate;

    // TODO ログ
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int insert(String accession) {
        return jdbcTemplate.update("insert into experiment(accession) values(?)", accession);
    }
}
