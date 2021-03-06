package com.ddbj.ld.data.beans.dra.run;

import com.fasterxml.jackson.annotation.*;

public class Directives {
    private String sampleDemuxDirective;

    @JsonProperty("SAMPLE_DEMUX_DIRECTIVE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSampleDemuxDirective() { return sampleDemuxDirective; }
    @JsonProperty("SAMPLE_DEMUX_DIRECTIVE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSampleDemuxDirective(String value) { this.sampleDemuxDirective = value; }
}
