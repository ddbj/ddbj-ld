package com.ddbj.ld.bean.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class AnalysisLinks {
    private AnalysisLink analysisLink;

    @JsonProperty("ANALYSIS_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AnalysisLink getAnalysisLink() { return analysisLink; }
    @JsonProperty("ANALYSIS_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisLink(AnalysisLink value) { this.analysisLink = value; }
}
