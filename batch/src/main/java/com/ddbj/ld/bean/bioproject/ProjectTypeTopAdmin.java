package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProjectTypeTopAdmin {
    private String subtype;
    private List<ProjectTypeTopAdminOrganism> organism;
    private Size genomeSize;
    private String descriptionSubtypeOther;

    @JsonProperty("subtype")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSubtype() { return subtype; }
    @JsonProperty("subtype")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubtype(String value) { this.subtype = value; }

    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ProjectTypeTopAdminOrganism> getOrganism() { return organism; }
    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganism(List<ProjectTypeTopAdminOrganism> value) { this.organism = value; }

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
