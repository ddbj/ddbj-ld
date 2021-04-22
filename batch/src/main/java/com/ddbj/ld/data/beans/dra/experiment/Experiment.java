package com.ddbj.ld.data.beans.dra.experiment;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;

public class Experiment implements IPropertiesBean {
    private EXPERIMENTClass experiment;

    @JsonProperty("EXPERIMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public EXPERIMENTClass getExperiment() { return experiment; }
    @JsonProperty("EXPERIMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExperiment(EXPERIMENTClass value) { this.experiment = value; }
}
