package com.ddbj.ld.bean.dra.run;

import com.fasterxml.jackson.annotation.*;

public class RunAttributes {
    private RunAttribute runAttribute;

    @JsonProperty("RUN_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RunAttribute getRunAttribute() { return runAttribute; }
    @JsonProperty("RUN_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRunAttribute(RunAttribute value) { this.runAttribute = value; }
}
