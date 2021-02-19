package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Locus {
    private String locusName;
    private Title description;
    private XrefLink probeSet;

    @JsonProperty("locus_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocusName() { return locusName; }
    @JsonProperty("locus_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocusName(String value) { this.locusName = value; }

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getDescription() { return description; }
    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(Title value) { this.description = value; }

    @JsonProperty("PROBE_SET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public XrefLink getProbeSet() { return probeSet; }
    @JsonProperty("PROBE_SET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProbeSet(XrefLink value) { this.probeSet = value; }
}
