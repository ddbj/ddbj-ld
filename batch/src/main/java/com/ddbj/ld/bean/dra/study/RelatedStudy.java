package com.ddbj.ld.bean.dra.study;

import com.fasterxml.jackson.annotation.*;

public class RelatedStudy {
    private Link relatedLink;
    private String isPrimary;

    @JsonProperty("RELATED_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Link getRelatedLink() { return relatedLink; }
    @JsonProperty("RELATED_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelatedLink(Link value) { this.relatedLink = value; }

    @JsonProperty("IS_PRIMARY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsPrimary() { return isPrimary; }
    @JsonProperty("IS_PRIMARY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIsPrimary(String value) { this.isPrimary = value; }
}
