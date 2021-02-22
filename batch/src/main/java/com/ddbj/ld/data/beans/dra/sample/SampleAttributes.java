package com.ddbj.ld.data.beans.dra.sample;

import com.fasterxml.jackson.annotation.*;

public class SampleAttributes {
    private SampleAttribute sampleAttribute;

    @JsonProperty("SAMPLE_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SampleAttribute getSampleAttribute() { return sampleAttribute; }
    @JsonProperty("SAMPLE_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSampleAttribute(SampleAttribute value) { this.sampleAttribute = value; }
}
