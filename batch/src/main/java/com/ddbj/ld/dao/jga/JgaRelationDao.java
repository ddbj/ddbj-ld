package com.ddbj.ld.dao.jga;

import com.ddbj.ld.bean.common.DBXrefsBean;
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
@AllArgsConstructor
@Slf4j
public class JgaRelationDao {
    private JdbcTemplate jdbcTemplate;
    private UrlHelper urlHelper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int[] bulkInsert(List<Object[]> recordList) {
        int[] argTypes = new int[4];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.VARCHAR;
        argTypes[3] = Types.VARCHAR;
        int[] results = new int[recordList.size()];

        String sql = "insert into jga_relation (self_accession, parent_accession, self_type, parent_type) values(?, ?, ?, ?)";

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
    public List<DBXrefsBean> selSelf(String accession) {
        String sql = "select * from jga_relation where self_accession = ?";

        jdbcTemplate.setFetchSize(1000);

        // FIXME EntityとDBXrefsBeanは分けたほうがよい、urlHelperも上の層で呼ぶべき
        List<DBXrefsBean> DBXrefsBeanList = jdbcTemplate.query(sql, new Object[]{ accession }, new RowMapper<DBXrefsBean>() {
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

    // FIXME EntityとDBXrefsBeanは分けたほうがよい、urlHelperも上の層で呼ぶべき
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<DBXrefsBean> selParent(String accession) {
        String sql = "select * from jga_relation where parent_accession = ?";

        jdbcTemplate.setFetchSize(1000);

        List<DBXrefsBean> DBXrefsBeanList = jdbcTemplate.query(sql, new Object[]{ accession }, new RowMapper<DBXrefsBean>() {
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
}
