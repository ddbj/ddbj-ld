package com.ddbj.ld.data.beans.dra.analysis;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;

public class Analysis implements IPropertiesBean {
    private ANALYSISClass analysis;

    @JsonProperty("ANALYSIS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ANALYSISClass getAnalysis() { return analysis; }
    @JsonProperty("ANALYSIS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysis(ANALYSISClass value) { this.analysis = value; }
}
