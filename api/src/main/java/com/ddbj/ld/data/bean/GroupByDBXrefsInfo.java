package com.ddbj.ld.data.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupByDBXrefsInfo {
    private String type;
    private List<LinkedHashMap<String, Object>> dbXrefs;
    private boolean hasMore;
    private String searchURL;
}
