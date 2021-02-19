package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class ReferenceAlignment {
    private Assembly assembly;
    private RunLabels runLabels;
    private SeqLabels seqLabels;
    private ReferenceAlignmentProcessing processing;

    @JsonProperty("ASSEMBLY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Assembly getAssembly() { return assembly; }
    @JsonProperty("ASSEMBLY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAssembly(Assembly value) { this.assembly = value; }

    @JsonProperty("RUN_LABELS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RunLabels getRunLabels() { return runLabels; }
    @JsonProperty("RUN_LABELS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRunLabels(RunLabels value) { this.runLabels = value; }

    @JsonProperty("SEQ_LABELS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SeqLabels getSeqLabels() { return seqLabels; }
    @JsonProperty("SEQ_LABELS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSeqLabels(SeqLabels value) { this.seqLabels = value; }

    @JsonProperty("PROCESSING")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ReferenceAlignmentProcessing getProcessing() { return processing; }
    @JsonProperty("PROCESSING")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProcessing(ReferenceAlignmentProcessing value) { this.processing = value; }
}
