package com.ddbj.ld.app.transact.dao.external;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.data.beans.common.BioDateBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly=true) // 外部DBであり参照オンリー
@AllArgsConstructor
@Slf4j
public class ExternalBioSampleDao {

    private final JdbcTemplate bioSampleJdbc;

    private final JsonModule jsonModule;

    public List<BioDateBean> all() {
        var sql = "SELECT " +
                "    a.accession_id AS accession, " +
                "    s.create_date AS date_created, " +
                "    s.release_date AS date_published, " +
                "    s.modified_date AS date_modified " +
                "FROM " +
                "    mass.accession a " +
                "    INNER JOIN " +
                "        mass.sample s" +
                "    ON  a.smp_id = s.smp_id " +
                ";";

        this.bioSampleJdbc.setFetchSize(1000);
        var resultList = this.bioSampleJdbc.query(sql, (rs, rowNum) -> this.jsonModule.getBioDate(rs));

        return resultList;
    }
}
