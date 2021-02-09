package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Size {
    private String units;
    private String content;

    @JsonProperty("units")
    public String getUnits() { return units; }
    @JsonProperty("units")
    public void setUnits(String value) { this.units = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
