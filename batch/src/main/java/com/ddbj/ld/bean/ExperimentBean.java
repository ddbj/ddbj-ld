package com.ddbj.ld.bean;

import lombok.Data;
import java.util.List;

@Data
public class ExperimentBean {
    private String object;

    private String identifier;

    private String dateCreated;

    private String dateModified;

    private String datePublished;

    private String name;

    private String title;

    private String description;

    private List<DBXrefsBean> dbXrefs;

    public ExperimentBean() {
        this.object = "XML2JSON";
    }
}

