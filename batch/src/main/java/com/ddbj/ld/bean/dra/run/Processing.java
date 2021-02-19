package com.ddbj.ld.bean.dra.run;

import com.fasterxml.jackson.annotation.*;

public class Processing {
    private Pipeline pipeline;
    private Directives directives;

    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Pipeline getPipeline() { return pipeline; }
    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPipeline(Pipeline value) { this.pipeline = value; }

    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Directives getDirectives() { return directives; }
    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDirectives(Directives value) { this.directives = value; }
}
