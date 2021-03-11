package com.ddbj.ld.data.beans.bioproject;

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
    public void setDescriptionSubtypeOther(String value) { this.descriptionSubtypeOther = value; }
}
