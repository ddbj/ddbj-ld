package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class OrganizationName {
    private String abbr;
    private String content;

    @JsonProperty("abbr")
    public String getAbbr() { return abbr; }
    @JsonProperty("abbr")
    public void setAbbr(String value) { this.abbr = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
