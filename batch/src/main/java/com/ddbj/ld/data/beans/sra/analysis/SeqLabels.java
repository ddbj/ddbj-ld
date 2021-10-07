package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;

public class SeqLabels {
    private Sequence sequence;

    @JsonProperty("SEQUENCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Sequence getSequence() { return sequence; }
    @JsonProperty("SEQUENCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSequence(Sequence value) { this.sequence = value; }
}
