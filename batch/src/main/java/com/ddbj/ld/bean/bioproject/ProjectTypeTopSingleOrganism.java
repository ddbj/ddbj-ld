package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

public class ProjectTypeTopSingleOrganism {
    private List<ProjectTypeTopSingleOrganismOrganism> organism;
    private Size genomeSize;

    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ProjectTypeTopSingleOrganismOrganism> getOrganism() { return organism; }
    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganism(List<ProjectTypeTopSingleOrganismOrganism> value) { this.organism = value; }

    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Size getGenomeSize() { return genomeSize; }
    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGenomeSize(Size value) { this.genomeSize = value; }
}
