package com.ddbj.ld.app.transact.dao.external;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.data.beans.common.BioDateBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true) // 外部DBであり参照オンリー
@AllArgsConstructor
@Slf4j
public class ExternalBioProjectDao {

    private final JdbcTemplate bioProjectJdbc;

    private final JsonModule jsonModule;

    public BioDateBean select(final String accession) {
        var sql = "SELECT " +
                "    s.accession AS accession, " +
                "    p.create_date AS date_created, " +
                "    p.release_date AS date_published, " +
                "    p.modified_date AS date_modified " +
                "FROM " +
                "    mass.bioproject_summary s " +
                "    INNER JOIN " +
                "        mass.project p " +
                "    ON  s.submission_id = p.submission_id " +
                "WHERE " +
                "    accession = ?" +
                ";";

        Object[] args = {
                accession
        };

        this.bioProjectJdbc.setFetchSize(1000);
        var resultList = this.bioProjectJdbc.query(sql, (rs, rowNum) -> this.jsonModule.getBioDate(rs), args);

        return resultList.size() == 0 ? null : resultList.get(0);
    }
}
