package com.ddbj.ld.bean.biosample;

import com.fasterxml.jackson.annotation.*;

public class NameElement {
    private String abbreviation;
    private String url;
    private String content;

    @JsonProperty("abbreviation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAbbreviation() { return abbreviation; }
    @JsonProperty("abbreviation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAbbreviation(String value) { this.abbreviation = value; }

    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getURL() { return url; }
    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setURL(String value) { this.url = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
