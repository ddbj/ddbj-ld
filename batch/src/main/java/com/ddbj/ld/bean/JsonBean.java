package com.ddbj.ld.bean;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.util.List;

@Data
public class JsonBean {
    private String identifier;

    private String type;

    private String url;

    // TODO
    private List<SameAsBean> sameAs;

    private String isPartOf;

    private OrganismBean organism;

    private List<DBXrefsBean> dbXrefs;

    @JsonRawValue
    private String properties;

    private List<DistributionBean> distribution;

    private String dateCreated;

    private String dateModified;

    private String datePublished;
}

