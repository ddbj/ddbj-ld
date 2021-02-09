package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectTypeTopAdmin {
    private String subtype;
    private ProjectTypeTopAdminOrganism organism;
    private Size genomeSize;
    private String descriptionSubtypeOther;

    @JsonProperty("subtype")
    public String getSubtype() { return subtype; }
    @JsonProperty("subtype")
    public void setSubtype(String value) { this.subtype = value; }

    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectTypeTopAdminOrganism getOrganism() { return organism; }
    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganism(ProjectTypeTopAdminOrganism value) { this.organism = value; }

    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Size getGenomeSize() { return genomeSize; }
    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGenomeSize(Size value) { this.genomeSize = value; }

    @JsonProperty("DescriptionSubtypeOther")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescriptionSubtypeOther() { return descriptionSubtypeOther; }
    @JsonProperty("DescriptionSubtypeOther")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescriptionSubtypeOther(String value) { this.descriptionSubtypeOther = value; }
}
