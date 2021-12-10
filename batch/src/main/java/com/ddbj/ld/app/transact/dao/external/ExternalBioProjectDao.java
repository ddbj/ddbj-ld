package com.ddbj.ld.app.transact.dao.external;

import com.ddbj.ld.app.core.module.JsonModule;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.data.beans.common.BioDateBean;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
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

    public List<DBXrefsBean> selChildAccession(final String accession) {
        var sql = "SELECT " +
                "    DISTINCT pp.accession AS accession " +
                "FROM mass.umbrella_info u " +
                "    INNER JOIN " +
                "        mass.bioproject_summary p " +
                "    ON  p.submission_id = u.parent_submission_id " +
                "    INNER JOIN " +
                "        mass.bioproject_summary pp " +
                "    ON  pp.submission_id = u.submission_id " +
                "WHERE\n" +
                "    p.entity_status = 1000 " +
                "AND pp.entity_status = 100 " +
                "AND p.accession = ? " +
                "ORDER BY accession " +
                ";";

        Object[] args = {
                accession
        };

        this.bioProjectJdbc.setFetchSize(1000);
        var resultList = this.bioProjectJdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.BIOPROJECT.type), args);

        return resultList;
    }

    public List<DBXrefsBean> selParentAccession(final String accession) {
        var sql = "SELECT " +
                "    DISTINCT p.accession AS accession " +
                "FROM mass.umbrella_info u " +
                "    INNER JOIN " +
                "        mass.bioproject_summary p " +
                "    ON  p.submission_id = u.parent_submission_id " +
                "    INNER JOIN " +
                "        mass.bioproject_summary pp " +
                "    ON  pp.submission_id = u.submission_id " +
                "WHERE\n" +
                "    p.entity_status = 1000 " +
                "AND pp.entity_status = 100 " +
                "AND pp.accession = ? " +
                "ORDER BY accession " +
                ";";

        Object[] args = {
                accession
        };

        this.bioProjectJdbc.setFetchSize(1000);
        var resultList = this.bioProjectJdbc.query(sql, (rs, rowNum) -> this.jsonModule.getDBXrefs(rs, TypeEnum.BIOPROJECT.type), args);

        return resultList;
    }
}
