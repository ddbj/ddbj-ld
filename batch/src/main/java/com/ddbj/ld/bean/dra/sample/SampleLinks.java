package com.ddbj.ld.bean.dra.sample;

import com.fasterxml.jackson.annotation.*;

public class SampleLinks {
    private SampleLink sampleLink;

    @JsonProperty("SAMPLE_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SampleLink getSampleLink() { return sampleLink; }
    @JsonProperty("SAMPLE_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSampleLink(SampleLink value) { this.sampleLink = value; }
}
