package com.ddbj.ld.app.transact.dao.jga;

import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.common.helper.UrlHelper;
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
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@AllArgsConstructor
@Slf4j
public class JgaRelationDao {
    private JdbcTemplate jdbcTemplate;
    private UrlHelper urlHelper;

    public int[] bulkInsert(List<Object[]> recordList) {
        int[] argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.VARCHAR;
        argTypes[3] = Types.VARCHAR;
        int[] results = new int[recordList.size()];

        String sql = "INSERT INTO jga_relation (self_accession, parent_accession, self_type, parent_type) VALUES (?, ?, ?, ?)";

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

    @Transactional(readOnly = true)
    public List<DBXrefsBean> selSelfAndParentType(String accession, String parentType) {
        String sql = "SELECT DISTINCT parent_accession, parent_type FROM jga_relation " +
                "WHERE self_accession = ? " +
                "  AND parent_type = ? " +
                "ORDER BY parent_accession";

        jdbcTemplate.setFetchSize(1000);

        List<DBXrefsBean> DBXrefsBeanList = jdbcTemplate.query(sql, new Object[]{ accession, parentType }, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    String identifier = rs.getString("parent_accession");
                    String type       = rs.getString("parent_type");
                    String url        = urlHelper.getUrl(type, identifier);

                    DBXrefsBean dbXrefsBean = new DBXrefsBean();
                    dbXrefsBean.setIdentifier(identifier);
                    dbXrefsBean.setType(type);
                    dbXrefsBean.setUrl(url);

                    return dbXrefsBean;
                } catch (SQLException e) {
                    log.debug(e.getMessage());

                    return null;
                }
            }
        });

        return DBXrefsBeanList;
    }

    @Transactional(readOnly = true)
    public List<DBXrefsBean> selParentAndSelfType(String accession, String selfType) {
        String sql = "SELECT DISTINCT self_accession, self_type FROM jga_relation " +
                "WHERE parent_accession = ? " +
                "  AND self_type = ? " +
                "ORDER BY self_accession";

        jdbcTemplate.setFetchSize(1000);

        List<DBXrefsBean> DBXrefsBeanList = jdbcTemplate.query(sql, new Object[]{ accession, selfType }, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    String identifier = rs.getString("self_accession");
                    String type       = rs.getString("self_type");
                    String url        = urlHelper.getUrl(type, identifier);

                    DBXrefsBean dbXrefsBean = new DBXrefsBean();
                    dbXrefsBean.setIdentifier(identifier);
                    dbXrefsBean.setType(type);
                    dbXrefsBean.setUrl(url);

                    return dbXrefsBean;
                } catch (SQLException e) {
                    log.debug(e.getMessage());

                    return null;
                }
            }
        });

        return DBXrefsBeanList;
    }

    @Transactional(readOnly = true)
    public List<DBXrefsBean> selDataset(String accession) {
        String sql = "SELECT DISTINCT ds.self_accession, ds.self_type " +
                     "FROM jga_relation st " +
                     "INNER JOIN jga_relation dt\n" +
                        "     ON st.self_accession = dt.parent_accession" +
                        "    AND dt.self_type = 'jga-data' " +
                     "INNER JOIN jga_relation ds" +
                     "        ON dt.self_accession = ds.parent_accession " +
                    "        AND ds.self_type = 'jga-dataset' " +
                     "WHERE st.parent_accession = ? " +
                   "    AND st.self_type    = 'jga-experiment' " +
                     "UNION " +
                     "SELECT DISTINCT ds.self_accession, ds.self_type " +
                     "FROM jga_relation st " +
                     "INNER JOIN jga_relation ds " +
                     "        ON st.self_accession = ds.parent_accession " +
                     "       AND ds.self_type = 'jga-dataset' " +
                     "WHERE st.parent_accession = ? " +
                     "    AND st.self_type    = 'jga-analysis' " +
                     "ORDER BY self_accession;";

        jdbcTemplate.setFetchSize(1000);

        List<DBXrefsBean> DBXrefsBeanList = jdbcTemplate.query(sql, new Object[]{ accession, accession }, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    String identifier = rs.getString("self_accession");
                    String type       = rs.getString("self_type");
                    String url        = urlHelper.getUrl(type, identifier);

                    DBXrefsBean dbXrefsBean = new DBXrefsBean();
                    dbXrefsBean.setIdentifier(identifier);
                    dbXrefsBean.setType(type);
                    dbXrefsBean.setUrl(url);

                    return dbXrefsBean;
                } catch (SQLException e) {
                    log.debug(e.getMessage());

                    return null;
                }
            }
        });

        return DBXrefsBeanList;
    }

    @Transactional(readOnly = true)
    public List<DBXrefsBean> selDAC() {
        String sql = "SELECT DISTINCT parent_accession, parent_type " +
                "FROM jga_relation " +
                "WHERE parent_type = 'jga-dac'";

        jdbcTemplate.setFetchSize(1000);

        List<DBXrefsBean> DBXrefsBeanList = jdbcTemplate.query(sql, new Object[]{ }, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    String identifier = rs.getString("parent_accession");
                    String type       = rs.getString("parent_type");
                    String url        = urlHelper.getUrl(type, identifier);

                    DBXrefsBean dbXrefsBean = new DBXrefsBean();
                    dbXrefsBean.setIdentifier(identifier);
                    dbXrefsBean.setType(type);
                    dbXrefsBean.setUrl(url);

                    return dbXrefsBean;
                } catch (SQLException e) {
                    log.debug(e.getMessage());

                    return null;
                }
            }
        });

        return DBXrefsBeanList;
    }

    public void deleteAll() {
        String sql = "DELETE FROM jga_relation";
        jdbcTemplate.update(sql);
    }
}
