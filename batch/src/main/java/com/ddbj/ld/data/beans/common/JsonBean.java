package com.ddbj.ld.data.beans.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private IPropertiesBean properties;

    private List<DistributionBean> distribution;

    private List<DownloadUrlBean> downloadUrl;

    private String status;

    private String visibility;

    private String dateCreated;

    private String dateModified;

    private String datePublished;
}

