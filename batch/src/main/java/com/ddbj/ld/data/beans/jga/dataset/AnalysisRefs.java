package com.ddbj.ld.data.beans.jga.dataset;

import com.fasterxml.jackson.annotation.*;

public class AnalysisRefs {
    private Ref analysisRef;

    @JsonProperty("ANALYSIS_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Ref getAnalysisRef() { return analysisRef; }
    @JsonProperty("ANALYSIS_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisRef(Ref value) { this.analysisRef = value; }
}
