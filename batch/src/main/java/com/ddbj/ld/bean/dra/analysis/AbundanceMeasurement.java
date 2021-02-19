package com.ddbj.ld.bean.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class AbundanceMeasurement {
    private AbundanceMeasurementProcessing processing;

    @JsonProperty("PROCESSING")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AbundanceMeasurementProcessing getProcessing() { return processing; }
    @JsonProperty("PROCESSING")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProcessing(AbundanceMeasurementProcessing value) { this.processing = value; }
}
