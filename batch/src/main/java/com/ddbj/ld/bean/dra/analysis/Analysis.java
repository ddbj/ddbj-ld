package com.ddbj.ld.bean.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class Analysis {
    private ANALYSISClass analysis;

    @JsonProperty("ANALYSIS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ANALYSISClass getAnalysis() { return analysis; }
    @JsonProperty("ANALYSIS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysis(ANALYSISClass value) { this.analysis = value; }
}
