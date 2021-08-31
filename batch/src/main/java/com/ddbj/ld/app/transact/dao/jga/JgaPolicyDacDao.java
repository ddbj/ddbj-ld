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

import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@AllArgsConstructor
@Slf4j
public class JgaPolicyDacDao implements JgaDao {

    private final JdbcTemplate jdbc;
    private final JsonModule jsonModule;

    @Override
    public void bulkInsert(final List<Object[]> recordList) {
        var argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;

        var sql = "INSERT INTO t_jga_policy_dac (policy_accession, dac_accession, created_at, updated_at) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {
            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_jga_policy_dac is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    @Override
    public void deleteAll() {
        this.jdbc.update("DELETE FROM t_jga_policy_dac");
    }

    @Override
    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_jga_policy_dac_01 ON t_jga_policy_dac (policy_accession);");
        this.jdbc.update("CREATE INDEX idx_jga_policy_dac_02 ON t_jga_policy_dac (dac_accession);");
    }

    @Override
    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_jga_policy_dac_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_jga_policy_dac_02;");
    }

    public List<DBXrefsBean> selAllPolicy() {
        var sql = "SELECT DISTINCT " +
                "    policy_accession AS accession " +
                "FROM " +
                "    t_jga_policy_dac " +
                "ORDER BY accession;";

        this.jdbc.setFetchSize(1000);


        return this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.JGA_POLICY.type));
    }
}
