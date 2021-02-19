package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;

public class StudyLink {
    private URLLink urlLink;
    private Link xrefLink;
    private Link entrezLink;

    @JsonProperty("URL_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public URLLink getURLLink() { return urlLink; }
    @JsonProperty("URL_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setURLLink(URLLink value) { this.urlLink = value; }

    @JsonProperty("XREF_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Link getXrefLink() { return xrefLink; }
    @JsonProperty("XREF_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setXrefLink(Link value) { this.xrefLink = value; }

    @JsonProperty("ENTREZ_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Link getEntrezLink() { return entrezLink; }
    @JsonProperty("ENTREZ_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEntrezLink(Link value) { this.entrezLink = value; }
}
