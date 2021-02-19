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
import java.util.Map;

@Repository
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@AllArgsConstructor
@Slf4j
public class JgaDateDao {
    private JdbcTemplate jdbcTemplate;

    public int[] bulkInsert(List<Object[]> recordList) {
        int[] argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.TIMESTAMP;
        argTypes[2] = Types.TIMESTAMP;
        argTypes[3] = Types.TIMESTAMP;

        int[] results = new int[recordList.size()];
        String sql = "insert into jga_date (accession, date_created, date_published, date_modified) values(?, ?, ?, ?)";

        try {
            results = jdbcTemplate.batchUpdate(
                    sql,
                    recordList, argTypes);
        } catch(Exception e) {
            log.debug(e.getMessage());
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        } finally {
            return results;
        }
    }

    public Map<String, Object> selJgaDate(String accession) {
        final var sql = "select * from jga_date where accession = ?";

        jdbcTemplate.setFetchSize(1000);

        Object[] args = {
                accession
        };

        return jdbcTemplate.queryForMap(sql, args);
    }

    public void deleteAll() {
        String sql = "delete from jga_date";
        jdbcTemplate.update(sql);
    }
}
