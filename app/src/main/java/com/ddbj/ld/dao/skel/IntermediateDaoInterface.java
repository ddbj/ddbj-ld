package com.ddbj.ld.dao.skel;

import java.util.List;

public interface IntermediateDaoInterface {
    public int insert(String firstAccession, String secondAccession);
    public int[] bulkInsert(List<Object[]> accessionList);
}
