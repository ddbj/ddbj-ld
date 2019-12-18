package com.ddbj.ld.common.dao;

import java.util.List;

public interface DaoInterface {
    public int insert(String accession);
    public int[] bulkInsert(List<Object[]> accessionList);
}
