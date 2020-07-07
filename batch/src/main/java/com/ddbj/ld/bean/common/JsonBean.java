package com.ddbj.ld.bean.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.util.Date;
import java.util.List;

// FIXME Elasticsearchに登録するJsonのもとのBeanは将来的にこれに統一
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date dateModified;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date datePublished;
}

