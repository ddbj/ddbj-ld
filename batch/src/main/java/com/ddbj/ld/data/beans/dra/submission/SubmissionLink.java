package com.ddbj.ld.data.beans.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class SubmissionLink {
    private URLLink urlLink;
    private XrefLink xrefLink;

    @JsonProperty("URL_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public URLLink getURLLink() { return urlLink; }
    @JsonProperty("URL_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setURLLink(URLLink value) { this.urlLink = value; }

    @JsonProperty("XREF_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public XrefLink getXrefLink() { return xrefLink; }
    @JsonProperty("XREF_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setXrefLink(XrefLink value) { this.xrefLink = value; }
}
