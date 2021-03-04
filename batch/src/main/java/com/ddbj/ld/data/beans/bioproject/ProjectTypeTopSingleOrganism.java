package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

<<<<<<< HEAD
public class ProjectTypeTopSingleOrganism {
    private Organism organism;

    @JsonProperty("Organism")
    public Organism getOrganism() { return organism; }
    @JsonProperty("Organism")
    public void setOrganism(Organism value) { this.organism = value; }
=======
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
>>>>>>> 取り込み、修正
}
