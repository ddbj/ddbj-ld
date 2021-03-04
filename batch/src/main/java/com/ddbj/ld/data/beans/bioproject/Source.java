package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Source {
    private String name;
    private String url;

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(String value) { this.name = value; }

    @JsonProperty("Url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getURL() { return url; }
    @JsonProperty("Url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setURL(String value) { this.url = value; }
}
