package com.ddbj.ld.data.beans.dra.study;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.XrefLink;
import com.fasterxml.jackson.annotation.*;

public class RelatedStudy {
    private XrefLink relatedLink;
=======
import com.fasterxml.jackson.annotation.*;

public class RelatedStudy {
    private Link relatedLink;
>>>>>>> 取り込み、修正
    private String isPrimary;

    @JsonProperty("RELATED_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<< HEAD
    public XrefLink getRelatedLink() { return relatedLink; }
    @JsonProperty("RELATED_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelatedLink(XrefLink value) { this.relatedLink = value; }
=======
    public Link getRelatedLink() { return relatedLink; }
    @JsonProperty("RELATED_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelatedLink(Link value) { this.relatedLink = value; }
>>>>>>> 取り込み、修正

    @JsonProperty("IS_PRIMARY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsPrimary() { return isPrimary; }
    @JsonProperty("IS_PRIMARY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIsPrimary(String value) { this.isPrimary = value; }
}
