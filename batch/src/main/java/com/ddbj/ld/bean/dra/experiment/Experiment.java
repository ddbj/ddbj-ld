package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Experiment {
    private EXPERIMENTClass experiment;

    @JsonProperty("EXPERIMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public EXPERIMENTClass getExperiment() { return experiment; }
    @JsonProperty("EXPERIMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExperiment(EXPERIMENTClass value) { this.experiment = value; }
}
