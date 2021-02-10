package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectTypeTopSingleOrganism {
    private ProjectTypeTopSingleOrganismOrganism organism;
    private Size genomeSize;

    @JsonProperty("Organism")
    public ProjectTypeTopSingleOrganismOrganism getOrganism() { return organism; }
    @JsonProperty("Organism")
    public void setOrganism(ProjectTypeTopSingleOrganismOrganism value) { this.organism = value; }

    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Size getGenomeSize() { return genomeSize; }
    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGenomeSize(Size value) { this.genomeSize = value; }
}
