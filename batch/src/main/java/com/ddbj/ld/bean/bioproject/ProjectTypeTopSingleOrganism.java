package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectTypeTopSingleOrganism {
    private Organism organism;

    @JsonProperty("Organism")
    public Organism getOrganism() { return organism; }
    @JsonProperty("Organism")
    public void setOrganism(Organism value) { this.organism = value; }
}
