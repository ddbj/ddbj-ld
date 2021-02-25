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

    public int[] bulkInsert(final List<Object[]> recordList) {
        var argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.VARCHAR;
        argTypes[3] = Types.VARCHAR;

        var sql = "INSERT INTO jga_relation (self_accession, parent_accession, self_type, parent_type) VALUES (?, ?, ?, ?)";

        try {

            return this.jdbcTemplate.batchUpdate(sql, recordList, argTypes);

        } catch(Exception e) {
            log.debug(e.getMessage());
            recordList.forEach(relation -> log.debug(Arrays.toString(relation)));

            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<DBXrefsBean> selSelfAndParentType(
            final String accession,
            final String parentType
    ) {
        var sql = "SELECT DISTINCT parent_accession, parent_type FROM jga_relation " +
                "WHERE self_accession = ? " +
                "  AND parent_type = ? " +
                "ORDER BY parent_accession";

        Object[] args = {
                accession,
                parentType
        };

        this.jdbcTemplate.setFetchSize(1000);

        var DBXrefsBeanList = this.jdbcTemplate.query(sql, args, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    var identifier = rs.getString("parent_accession");
                    var type       = rs.getString("parent_type");
                    var url        = urlHelper.getUrl(type, identifier);

                    var dbXrefsBean = new DBXrefsBean();
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
    public List<DBXrefsBean> selParentAndSelfType(
            final String accession,
            final String selfType
    ) {
        var sql = "SELECT DISTINCT self_accession, self_type FROM jga_relation " +
                "WHERE parent_accession = ? " +
                "  AND self_type = ? " +
                "ORDER BY self_accession";

        this.jdbcTemplate.setFetchSize(1000);

        var DBXrefsBeanList = this.jdbcTemplate.query(sql, new Object[]{ accession, selfType }, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    var identifier = rs.getString("self_accession");
                    var type       = rs.getString("self_type");
                    var url        = urlHelper.getUrl(type, identifier);

                    var dbXrefsBean = new DBXrefsBean();
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
    public List<DBXrefsBean> selDatasetBelongsToStudy(final String accession) {
        var sql = "SELECT DISTINCT ds.self_accession, ds.self_type " +
                     "FROM jga_relation st " +
                     "INNER JOIN jga_relation dt " +
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

        this.jdbcTemplate.setFetchSize(1000);

        var DBXrefsBeanList = this.jdbcTemplate.query(sql, new Object[]{ accession, accession }, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    var identifier = rs.getString("self_accession");
                    var type       = rs.getString("self_type");
                    var url        = urlHelper.getUrl(type, identifier);

                    var dbXrefsBean = new DBXrefsBean();
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
    public List<DBXrefsBean> selStudyBelongsToPolicy(final String accession) {
        var sql = "SELECT DISTINCT st.parent_accession, st.parent_type " +
                "FROM jga_relation ds " +
                "INNER JOIN jga_relation dt " +
                "        ON ds.self_accession = dt.self_accession " +
                "       AND dt.parent_type = 'jga-data' " +
                "INNER JOIN jga_relation ex " +
                "        ON dt.parent_accession = ex.self_accession " +
                "       AND ex.parent_type = 'jga-experiment' " +
                "INNER JOIN jga_relation st " +
                "        ON ex.parent_accession = st.self_accession " +
                "       AND st.parent_type = 'jga-study' " +
                "WHERE ds.parent_accession = ? " +
                "  AND ds.self_type = 'jga-dataset' " +
                "UNION " +
                "SELECT DISTINCT st.parent_accession, st.parent_type FROM jga_relation ds " +
                "INNER JOIN jga_relation st " +
                "        ON ds.parent_accession = st.self_accession " +
                "       AND st.parent_type = 'jga-study' " +
                "WHERE ds.parent_accession = ? " +
                "  AND ds.self_type = 'jga-dataset' " +
                "ORDER BY parent_accession;";

        this.jdbcTemplate.setFetchSize(1000);

        var DBXrefsBeanList = jdbcTemplate.query(sql, new Object[]{ accession, accession }, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    var identifier = rs.getString("parent_accession");
                    var type       = rs.getString("parent_type");
                    var url        = urlHelper.getUrl(type, identifier);

                    var dbXrefsBean = new DBXrefsBean();
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
        var sql = "SELECT DISTINCT parent_accession, parent_type " +
                "FROM jga_relation " +
                "WHERE parent_type = 'jga-dac'";

        this.jdbcTemplate.setFetchSize(1000);

        var DBXrefsBeanList = this.jdbcTemplate.query(sql, new Object[]{ }, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    var identifier = rs.getString("parent_accession");
                    var type       = rs.getString("parent_type");
                    var url        = urlHelper.getUrl(type, identifier);

                    var dbXrefsBean = new DBXrefsBean();
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
    public List<DBXrefsBean> selDistinctParentAndParentType(final String parentType) {
        var sql = "SELECT DISTINCT parent_accession, parent_type FROM jga_relation " +
                "WHERE parent_type = ? " +
                "ORDER BY parent_accession";

        Object[] args = {
                parentType
        };

        this.jdbcTemplate.setFetchSize(1000);

        List<DBXrefsBean> DBXrefsBeanList = this.jdbcTemplate.query(sql, args, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    var identifier = rs.getString("parent_accession");
                    var type       = rs.getString("parent_type");
                    var url        = urlHelper.getUrl(type, identifier);

                    var dbXrefsBean = new DBXrefsBean();
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
    public List<DBXrefsBean> selDistinctSelfAndSelfType(final String selfType) {
        var sql = "SELECT DISTINCT self_accession, self_type FROM jga_relation " +
                "WHERE self_type = ? " +
                "ORDER BY self_accession";

        this.jdbcTemplate.setFetchSize(1000);

        Object[] args = {
                selfType
        };

        var DBXrefsBeanList = this.jdbcTemplate.query(sql, args, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    var identifier = rs.getString("self_accession");
                    var type       = rs.getString("self_type");
                    var url        = urlHelper.getUrl(type, identifier);

                    var dbXrefsBean = new DBXrefsBean();
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
        var sql = "DELETE FROM jga_relation";
        this.jdbcTemplate.update(sql);
    }
}
