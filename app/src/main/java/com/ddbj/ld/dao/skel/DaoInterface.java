package com.ddbj.ld.dao.skel;

import java.util.List;

public interface DaoInterface {
    public int insert(String accession);
    public int[] bulkInsert(List<Object[]> accessionList);
}
