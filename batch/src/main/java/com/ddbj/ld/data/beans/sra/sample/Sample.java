package com.ddbj.ld.data.beans.sra.sample;

import com.fasterxml.jackson.annotation.*;

public class Sample {
    private SAMPLEClass sample;

    @JsonProperty("SAMPLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SAMPLEClass getSample() { return sample; }
    @JsonProperty("SAMPLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSample(SAMPLEClass value) { this.sample = value; }
}
