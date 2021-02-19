package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class ExperimentLinks {
    private ExperimentLink experimentLink;

    @JsonProperty("EXPERIMENT_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ExperimentLink getExperimentLink() { return experimentLink; }
    @JsonProperty("EXPERIMENT_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExperimentLink(ExperimentLink value) { this.experimentLink = value; }
}
