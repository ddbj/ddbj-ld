package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class TargetedLoci {
    private Locus locus;

    @JsonProperty("LOCUS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Locus getLocus() { return locus; }
    @JsonProperty("LOCUS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocus(Locus value) { this.locus = value; }
}
