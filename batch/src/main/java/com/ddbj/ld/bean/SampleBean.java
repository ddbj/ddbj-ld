package com.ddbj.ld.bean;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import java.util.List;

@Data
public class SampleBean {
    private String identifier;

    private String dateCreated;

    private String dateModified;

    private String datePublished;

    private String name;

    private String title;

    private String description;

    private List<DBXrefsBean> dbXrefs;

    @JsonRawValue
    private String properties;
}

