package com.ddbj.ld.dao.jga;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class JgaDateDao {
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<String[]> selJgaDate(String accession) {
        String sql = "select * from jga_date where accession = ?";

        jdbcTemplate.setFetchSize(1000);

        List<String[]> jgaDateList = jdbcTemplate.query(sql, new Object[]{ accession }, new RowMapper<String[]>() {
            public String[] mapRow(ResultSet rs, int rowNum) {
                try {
                    String[] dateArr = new String[3];
                    dateArr[0] = rs.getString("date_created");
                    dateArr[1] = rs.getString("date_published");
                    dateArr[2] = rs.getString("date_modified");

                    return dateArr;
                } catch (SQLException e) {
                    log.debug(e.getMessage());

                    return null;
                }
            }
        });

        return jgaDateList;
    }
}
