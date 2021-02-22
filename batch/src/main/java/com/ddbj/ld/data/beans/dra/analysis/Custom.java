package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class Custom {
    private String description;
    private AnalysisLink referenceSource;

    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("REFERENCE_SOURCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AnalysisLink getReferenceSource() { return referenceSource; }
    @JsonProperty("REFERENCE_SOURCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReferenceSource(AnalysisLink value) { this.referenceSource = value; }
}
