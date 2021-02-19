package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class URLLink {
    private Title label;
    private String url;

    @JsonProperty("LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getLabel() { return label; }
    @JsonProperty("LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(Title value) { this.label = value; }

    @JsonProperty("URL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getURL() { return url; }
    @JsonProperty("URL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setURL(String value) { this.url = value; }
}
