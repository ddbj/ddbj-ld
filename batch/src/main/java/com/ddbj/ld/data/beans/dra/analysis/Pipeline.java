package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class Pipeline {
    private PipeSection pipeSection;

    @JsonProperty("PIPE_SECTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PipeSection getPipeSection() { return pipeSection; }
    @JsonProperty("PIPE_SECTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPipeSection(PipeSection value) { this.pipeSection = value; }
}
