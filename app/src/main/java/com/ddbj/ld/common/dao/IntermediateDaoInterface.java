package com.ddbj.ld.common.dao;

import java.util.List;

public interface IntermediateDaoInterface {
    public int insert(String firstAccession, String secondAccession);
    public int[] bulkInsert(List<Object[]> accessionList);
}
