package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Size {
    private String units;
    private String content;

    @JsonProperty("units")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUnits() { return units; }
    @JsonProperty("units")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUnits(String value) { this.units = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
