package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Source {
    private String name;
    private String url;

    @JsonProperty("Name")
    public String getName() { return name; }
    @JsonProperty("Name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("Url")
    public String getURL() { return url; }
    @JsonProperty("Url")
    public void setURL(String value) { this.url = value; }
}
