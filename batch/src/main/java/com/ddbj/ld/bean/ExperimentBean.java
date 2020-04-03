package com.ddbj.ld.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

@Data
public class ExperimentBean {
    @Value("XML2JSON")
    private String object;

    private String identifier;

    private String dateCreated;

    private String dateModified;

    private String datePublished;

    private String name;

    private String title;

    private String description;

    private List<DBXrefsBean> dbXrefs;
}

