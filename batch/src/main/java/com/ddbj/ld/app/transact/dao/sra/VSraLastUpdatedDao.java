package com.ddbj.ld.app.transact.dao.sra;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@AllArgsConstructor
@Slf4j
public class VSraLastUpdatedDao {

    private final JdbcTemplate jdbc;

    public LocalDateTime select() {
        var sql = "SELECT * FROM v_sra_last_updated;";

        var result = this.jdbc.queryForRowSet(sql);

        Timestamp lastUpdated = null;

        if(result.next()) {
            lastUpdated = result.getTimestamp("last_updated");
        }

        return null == lastUpdated ? null : lastUpdated.toLocalDateTime();
    }
}
