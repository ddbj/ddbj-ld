package com.ddbj.ld.data.beans.dra.experiment;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.XrefLink;
=======
>>>>>>> 取り込み、修正
import com.fasterxml.jackson.annotation.*;

public class Locus {
    private String locusName;
<<<<<<< HEAD
    private String description;
=======
    private Title description;
>>>>>>> 取り込み、修正
    private XrefLink probeSet;

    @JsonProperty("locus_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocusName() { return locusName; }
    @JsonProperty("locus_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocusName(String value) { this.locusName = value; }

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<< HEAD
    public String getDescription() { return description; }
    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }
=======
    public Title getDescription() { return description; }
    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(Title value) { this.description = value; }
>>>>>>> 取り込み、修正

    @JsonProperty("PROBE_SET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public XrefLink getProbeSet() { return probeSet; }
    @JsonProperty("PROBE_SET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProbeSet(XrefLink value) { this.probeSet = value; }
}
