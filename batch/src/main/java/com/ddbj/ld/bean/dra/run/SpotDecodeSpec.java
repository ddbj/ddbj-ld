package com.ddbj.ld.bean.dra.run;

import com.fasterxml.jackson.annotation.*;

public class SpotDecodeSpec {
    private String spotLength;
    private ReadSpec readSpec;

    @JsonProperty("SPOT_LENGTH")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSpotLength() { return spotLength; }
    @JsonProperty("SPOT_LENGTH")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSpotLength(String value) { this.spotLength = value; }

    @JsonProperty("READ_SPEC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ReadSpec getReadSpec() { return readSpec; }
    @JsonProperty("READ_SPEC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadSpec(ReadSpec value) { this.readSpec = value; }
}
