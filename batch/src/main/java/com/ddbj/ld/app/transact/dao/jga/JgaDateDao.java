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

    public int[] bulkInsert(final List<Object[]> recordList) {

        int[] argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.TIMESTAMP;
        argTypes[2] = Types.TIMESTAMP;
        argTypes[3] = Types.TIMESTAMP;

        var sql = "INSERT INTO jga_date (accession, date_created, date_published, date_modified) VALUES (?, ?, ?, ?)";

        try {

            return this.jdbcTemplate.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.debug(e.getMessage());
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));

            return null;
        }
    }

    public Map<String, Object> selJgaDate(final String accession) {
        var sql = "SELECT * FROM jga_date WHERE accession = ?";

        this.jdbcTemplate.setFetchSize(1000);

        Object[] args = {
                accession
        };

        return this.jdbcTemplate.queryForMap(sql, args);
    }

    public void deleteAll() {
        var sql = "DELETE FROM jga_date";

        this.jdbcTemplate.update(sql);
    }
}
