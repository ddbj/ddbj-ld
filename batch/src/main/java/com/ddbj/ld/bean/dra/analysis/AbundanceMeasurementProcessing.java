package com.ddbj.ld.bean.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class AbundanceMeasurementProcessing {
    private Pipeline pipeline;

    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Pipeline getPipeline() { return pipeline; }
    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPipeline(Pipeline value) { this.pipeline = value; }
}
