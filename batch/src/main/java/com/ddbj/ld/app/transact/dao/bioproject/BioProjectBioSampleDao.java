package com.ddbj.ld.app.transact.dao.bioproject;

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
public class BioProjectBioSampleDao {

    private final JdbcTemplate jdbc;

    private final JsonModule jsonModule;

    public void bulkInsert(final List<Object[]> recordList) {
        var argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;

        var sql = "INSERT INTO t_bioproject_biosample (bioproject_accession, biosample_accession, created_at, updated_at) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {
            this.jdbc.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.error("Registration to t_bioproject_biosample is failed.", e);
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));
        }
    }

    public void deleteAll() {
        this.jdbc.update("DELETE FROM t_bioproject_biosample");
    }


    public void createIndex() {
        this.jdbc.update("CREATE INDEX idx_bioproject_biosample_01 ON t_bioproject_biosample (bioproject_accession);");
        this.jdbc.update("CREATE INDEX idx_bioproject_biosample_02 ON t_bioproject_biosample (biosample_accession);");
    }


    public void dropIndex() {
        this.jdbc.update("DROP INDEX IF EXISTS idx_bioproject_biosample_01;");
        this.jdbc.update("DROP INDEX IF EXISTS idx_bioproject_biosample_02;");
    }

    public List<DBXrefsBean> selBioProject(
            final String biosampleAccession
    ) {
        var sql = "SELECT DISTINCT " +
                "    bioproject_accession AS accession " +
                "FROM " +
                "    t_bioproject_biosample " +
                "WHERE " +
                "    biosample_accession = ? " +
                "ORDER BY accession;";

        Object[] args = {
                biosampleAccession
        };

        this.jdbc.setFetchSize(1000);


        var records = this.jdbc.query(sql, (rs, rowNum) -> {
            try {
                var bean = new DBXrefsBean();
                var identifier = rs.getString("accession");

                bean.setIdentifier(identifier);
                bean.setType(TypeEnum.BIOPROJECT.type);
                bean.setUrl(jsonModule.getUrl(TypeEnum.BIOPROJECT.type, identifier));

                return bean;

            } catch (SQLException e) {
                log.error("Query is failed.", e);

                return null;
            }
        }, args);

        return records;
    }
}
