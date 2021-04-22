package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class AnalysisAttributes {
    private AnalysisAttribute analysisAttribute;

    @JsonProperty("ANALYSIS_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AnalysisAttribute getAnalysisAttribute() { return analysisAttribute; }
    @JsonProperty("ANALYSIS_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisAttribute(AnalysisAttribute value) { this.analysisAttribute = value; }
}
