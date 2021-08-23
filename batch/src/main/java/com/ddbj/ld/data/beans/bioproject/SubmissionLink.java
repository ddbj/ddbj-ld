package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmissionLink {
    private URLLink urlLink;
    private XrefLink xrefLink;

    @JsonProperty("URL_LINK")
    public URLLink getURLLink() { return urlLink; }
    @JsonProperty("URL_LINK")
    public void setURLLink(URLLink value) { this.urlLink = value; }

    @JsonProperty("XREF_LINK")
    public XrefLink getXrefLink() { return xrefLink; }
    @JsonProperty("XREF_LINK")
    public void setXrefLink(XrefLink value) { this.xrefLink = value; }
}
