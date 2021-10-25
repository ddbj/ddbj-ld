package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;

public class RunLabels {
    private Run run;

    @JsonProperty("RUN")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Run getRun() { return run; }
    @JsonProperty("RUN")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRun(Run value) { this.run = value; }
}
