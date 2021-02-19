package com.ddbj.ld.bean.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class Standard {
    private String shortName;
    private XrefLink name;

    @JsonProperty("short_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getShortName() { return shortName; }
    @JsonProperty("short_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setShortName(String value) { this.shortName = value; }

    @JsonProperty("NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public XrefLink getName() { return name; }
    @JsonProperty("NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(XrefLink value) { this.name = value; }
}
