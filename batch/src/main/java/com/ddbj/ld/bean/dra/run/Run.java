package com.ddbj.ld.bean.dra.run;

import com.fasterxml.jackson.annotation.*;

public class Run {
    private RUNClass run;

    @JsonProperty("RUN")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RUNClass getRun() { return run; }
    @JsonProperty("RUN")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRun(RUNClass value) { this.run = value; }
}
