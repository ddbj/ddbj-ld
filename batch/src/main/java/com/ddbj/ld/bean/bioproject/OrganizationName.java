package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class OrganizationName {
    private String abbr;
    private String content;

    @JsonProperty("abbr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAbbr() { return abbr; }
    @JsonProperty("abbr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAbbr(String value) { this.abbr = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
