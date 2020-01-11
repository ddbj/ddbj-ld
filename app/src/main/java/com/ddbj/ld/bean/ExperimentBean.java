package com.ddbj.ld.bean;

import lombok.Data;

import java.util.List;

@Data
public class ExperimentBean {
    private String identifier;

    private String name;

    private String title;

    private String description;

    private List<DBXrefsBean> dbXrefs;
}

