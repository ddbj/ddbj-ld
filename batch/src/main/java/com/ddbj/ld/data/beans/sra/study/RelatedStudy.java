package com.ddbj.ld.data.beans.sra.study;

import com.ddbj.ld.data.beans.sra.common.XrefLink;
import com.fasterxml.jackson.annotation.*;

public class RelatedStudy {
    private XrefLink relatedLink;
    private String isPrimary;

    @JsonProperty("RELATED_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public XrefLink getRelatedLink() { return relatedLink; }
    @JsonProperty("RELATED_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelatedLink(XrefLink value) { this.relatedLink = value; }

    @JsonProperty("IS_PRIMARY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsPrimary() { return isPrimary; }
    @JsonProperty("IS_PRIMARY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIsPrimary(String value) { this.isPrimary = value; }
}
