package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class ExperimentAttributes {
    private ExperimentAttribute experimentAttribute;

    @JsonProperty("EXPERIMENT_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ExperimentAttribute getExperimentAttribute() { return experimentAttribute; }
    @JsonProperty("EXPERIMENT_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExperimentAttribute(ExperimentAttribute value) { this.experimentAttribute = value; }
}
