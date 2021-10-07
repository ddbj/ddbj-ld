package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;

public class Processing {
    private Pipeline pipeline;
    private SequencingDirectives directives;

    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Pipeline getPipeline() { return pipeline; }
    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPipeline(Pipeline value) { this.pipeline = value; }

    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SequencingDirectives getDirectives() { return directives; }
    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDirectives(SequencingDirectives value) { this.directives = value; }
}
