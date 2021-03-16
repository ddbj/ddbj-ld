package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Locus {
    private String locusName;
    private String description;
    private XrefLink probeSet;

    @JsonProperty("locus_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocusName() { return locusName; }
    @JsonProperty("locus_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocusName(String value) { this.locusName = value; }

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("PROBE_SET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public XrefLink getProbeSet() { return probeSet; }
    @JsonProperty("PROBE_SET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProbeSet(XrefLink value) { this.probeSet = value; }
}
