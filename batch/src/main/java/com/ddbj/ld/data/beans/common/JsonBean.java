package com.ddbj.ld.data.beans.common;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// FIXME Elasticsearchに登録するJsonのもとのBeanは将来的にこれに統一
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonBean {
    private String identifier;

    private String title;

    private String description;

    private String name;

    private String type;

    private String url;

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

