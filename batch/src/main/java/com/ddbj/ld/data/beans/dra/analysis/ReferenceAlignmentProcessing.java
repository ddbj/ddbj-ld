package com.ddbj.ld.data.beans.dra.analysis;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.AlignmentDirectives;
import com.ddbj.ld.data.beans.dra.common.Pipeline;
=======
>>>>>>> 取り込み、修正
import com.fasterxml.jackson.annotation.*;

public class ReferenceAlignmentProcessing {
    private Pipeline pipeline;
<<<<<<< HEAD
    private AlignmentDirectives directives;
=======
    private Directives directives;
>>>>>>> 取り込み、修正

    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Pipeline getPipeline() { return pipeline; }
    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPipeline(Pipeline value) { this.pipeline = value; }

    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<< HEAD
    public AlignmentDirectives getDirectives() { return directives; }
    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDirectives(AlignmentDirectives value) { this.directives = value; }
=======
    public Directives getDirectives() { return directives; }
    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDirectives(Directives value) { this.directives = value; }
>>>>>>> 取り込み、修正
}
