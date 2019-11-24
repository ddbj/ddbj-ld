package com.ddbj.ld.dao;

import com.ddbj.ld.common.dao.DaoInterface;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;

@Repository
@AllArgsConstructor
public class SampleDao implements DaoInterface {
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int insert(String accession) {
        return jdbcTemplate.update("insert into sample(accession) values(?)", accession);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int[] bulkInsert(List<Object[]> accessionList) {
        // TODO ここの引数の型を指定しているのが早くするポイント、らしい要確認
        //  - https://kotaeta.com/57412729
        int[] argTypes = new int[1];
        argTypes[0] = Types.VARCHAR;

        int[] updateCounts = jdbcTemplate.batchUpdate(
                "insert into sample(accession) values(?)",
                accessionList, argTypes);

        return updateCounts;
    }
}
