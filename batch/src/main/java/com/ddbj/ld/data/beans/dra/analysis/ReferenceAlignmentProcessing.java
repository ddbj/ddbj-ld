package com.ddbj.ld.data.beans.dra.analysis;

import com.ddbj.ld.data.beans.dra.common.AlignmentDirectives;
import com.ddbj.ld.data.beans.dra.common.Pipeline;
import com.fasterxml.jackson.annotation.*;

public class ReferenceAlignmentProcessing {
    private Pipeline pipeline;
    private AlignmentDirectives directives;

    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Pipeline getPipeline() { return pipeline; }
    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPipeline(Pipeline value) { this.pipeline = value; }

    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AlignmentDirectives getDirectives() { return directives; }
    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDirectives(AlignmentDirectives value) { this.directives = value; }
}
