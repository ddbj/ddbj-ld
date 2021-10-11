package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;

public class SpotDescriptor {
    private SpotDecodeSpec spotDecodeSpec;

    @JsonProperty("SPOT_DECODE_SPEC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SpotDecodeSpec getSpotDecodeSpec() { return spotDecodeSpec; }
    @JsonProperty("SPOT_DECODE_SPEC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSpotDecodeSpec(SpotDecodeSpec value) { this.spotDecodeSpec = value; }
}
