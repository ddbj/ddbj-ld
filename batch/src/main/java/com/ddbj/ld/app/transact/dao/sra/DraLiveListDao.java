package com.ddbj.ld.app.transact.dao.sra;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.data.beans.common.DraLiveListBean;
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
public class DraLiveListDao {

    private final JdbcTemplate jdbc;

    private final JsonModule jsonModule;

    public void bulkInsert(final List<Object[]> recordList) {

        int[] argTypes = new int[20];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.VARCHAR;
        argTypes[3] = Types.TIMESTAMP;
        argTypes[4] = Types.VARCHAR;
        argTypes[5] = Types.VARCHAR;
        argTypes[6] = Types.VARCHAR;
        argTypes[7] = Types.VARCHAR;
        argTypes[8] = Types.VARCHAR;

        var sql = "INSERT INTO t_dra_livelist (" +
                "accession, submission, visibility, updated, type, center, alias, md5sum, created_at, updated_at) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {

            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_dra_livelist is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE from t_dra_livelist");
    }

    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_dra_livelist_01 ON t_dra_livelist (accession);");
        this.jdbc.update("CREATE INDEX idx_dra_livelist_02 ON t_dra_livelist (submission);");
    }

    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_livelist_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_dra_livelist_02;");
    }

    public List<DraLiveListBean> selSubmissionList() {
        var sql = "SELECT * " +
                "FROM t_dra_livelist " +
                "WHERE type = 'SUBMISSION'";

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDraLiveList(rs));

        return resultList;
    }

    public DraLiveListBean select(final String accession) {
        var sql = "SELECT * " +
                "FROM t_dra_livelist " +
                "WHERE accession = ?";

        Object[] args = {
                accession
        };

        this.jdbc.setFetchSize(1000);
        var resultList = this.jdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDraLiveList(rs), args);

        return resultList.size() > 0 ? resultList.get(0) : null;
    }
}
