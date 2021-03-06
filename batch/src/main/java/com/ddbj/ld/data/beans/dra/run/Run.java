package com.ddbj.ld.data.beans.dra.run;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;

public class Run implements IPropertiesBean {
    private RUNClass run;

    @JsonProperty("RUN")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RUNClass getRun() { return run; }
    @JsonProperty("RUN")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRun(RUNClass value) { this.run = value; }
}
