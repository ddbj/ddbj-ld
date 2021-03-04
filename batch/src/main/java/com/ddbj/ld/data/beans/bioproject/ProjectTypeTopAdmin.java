package com.ddbj.ld.data.beans.bioproject;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.*;

public class ProjectTypeTopAdmin {
    private String subtype;
    private Organism organism;
    private String descriptionSubtypeOther;

    @JsonProperty("subtype")
    public String getSubtype() { return subtype; }
    @JsonProperty("subtype")
    public void setSubtype(String value) { this.subtype = value; }

    @JsonProperty("Organism")
    public Organism getOrganism() { return organism; }
    @JsonProperty("Organism")
    public void setOrganism(Organism value) { this.organism = value; }

    @JsonProperty("DescriptionSubtypeOther")
    public String getDescriptionSubtypeOther() { return descriptionSubtypeOther; }
    @JsonProperty("DescriptionSubtypeOther")
=======
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
>>>>>>> 取り込み、修正
    public void setDescriptionSubtypeOther(String value) { this.descriptionSubtypeOther = value; }
}
