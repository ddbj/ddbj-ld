package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class ExpectedBasecallTable {
    private String defaultLength;
    private String baseCoord;
    private Basecall basecall;

    @JsonProperty("default_length")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDefaultLength() { return defaultLength; }
    @JsonProperty("default_length")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDefaultLength(String value) { this.defaultLength = value; }

    @JsonProperty("base_coord")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBaseCoord() { return baseCoord; }
    @JsonProperty("base_coord")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBaseCoord(String value) { this.baseCoord = value; }

    @JsonProperty("BASECALL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Basecall getBasecall() { return basecall; }
    @JsonProperty("BASECALL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBasecall(Basecall value) { this.basecall = value; }
}
