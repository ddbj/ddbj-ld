package com.ddbj.ld.dao;

import com.ddbj.ld.bean.DBXrefsBean;
import com.ddbj.ld.common.TypeEnum;
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
public class SRAAccessionsDao {
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int[] bulkInsert(String type, List<Object[]> recordList) {
        int[] argTypes = new int[5];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.TIMESTAMP;
        argTypes[2] = Types.TIMESTAMP;
        argTypes[3] = Types.TIMESTAMP;
        argTypes[4] = Types.VARCHAR;
        int[] results = new int[recordList.size()];

        String sql = "insert into " + type + "(accession, updated, published, received, visibility) values(?, ?, ?, ?, ?)";

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

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int[] bulkInsertRelation(String baseType, String targetType, List<Object[]> relationList) {
        int[] argTypes = new int[2];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        int[] results = new int[relationList.size()];

        String sql = "insert into "+ baseType + "_" + targetType + "(" + baseType + "_accession," + targetType + "_accession) values(?, ?)";

        try {
            results = jdbcTemplate.batchUpdate(
                    sql,
                    relationList, argTypes);
        } catch(Exception e) {
            log.debug(e.getMessage());
            relationList.forEach(relation -> log.debug(Arrays.toString(relation)));
        } finally {
            return results;
        }
    }

    public List<DBXrefsBean> selRelation(String accession, String tableName, TypeEnum baseType, TypeEnum targetType) {
        String baseAccession = baseType.getType() + "_accession";
        String targetAccession = targetType.getType() + "_accession";
        String sql = "select * from " + tableName + " where " + baseAccession + "= ?";

        List<DBXrefsBean> DBXrefsBeanList = jdbcTemplate.query(sql, new Object[]{ accession }, new RowMapper<DBXrefsBean>() {
            public DBXrefsBean mapRow(ResultSet rs, int rowNum) {
                try {
                    DBXrefsBean dbXrefsBean = new DBXrefsBean();
                    dbXrefsBean.setIdentifier(rs.getString(targetAccession));
                    dbXrefsBean.setType(targetType);
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
