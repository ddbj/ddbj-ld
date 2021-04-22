package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class URLLink {
    private String label;
    private String url;

    @JsonProperty("LABEL")
    public String getLabel() { return label; }
    @JsonProperty("LABEL")
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("URL")
    public String getURL() { return url; }
    @JsonProperty("URL")
    public void setURL(String value) { this.url = value; }
}
