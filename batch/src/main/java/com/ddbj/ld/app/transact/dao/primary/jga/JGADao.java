package com.ddbj.ld.app.transact.dao.primary.jga;

import java.util.List;

// リレーションの情報を扱うDaoを同一視したいためのDao
public interface JGADao {

    void bulkInsert(final List<Object[]> recordList);

    void deleteAll();

    void createIndex();

    void dropIndex();
}
