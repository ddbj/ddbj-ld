package com.ddbj.ld.data.beans.sra.analysis;

import com.ddbj.ld.data.beans.sra.common.Pipeline;
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
