package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class ProjectTypeTopSingleOrganismOrganism {
    private Size genomeSize;
    private String taxID;
    private String species;
    private String organismName;
    private String label;
    private String strain;
    private String supergroup;
    private BiologicalProperties biologicalProperties;
    private String organization;
    private String reproduction;
    private PurpleRepliconSet repliconSet;
    private Ploidy ploidy;
    private List<Count> count;
    private String replicon;

    @JsonProperty("GenomeSize")
    public Size getGenomeSize() { return genomeSize; }
    @JsonProperty("GenomeSize")
    public void setGenomeSize(Size value) { this.genomeSize = value; }

    @JsonProperty("taxID")
    public String getTaxID() { return taxID; }
    @JsonProperty("taxID")
    public void setTaxID(String value) { this.taxID = value; }

    @JsonProperty("species")
    public String getSpecies() { return species; }
    @JsonProperty("species")
    public void setSpecies(String value) { this.species = value; }

    @JsonProperty("OrganismName")
    public String getOrganismName() { return organismName; }
    @JsonProperty("OrganismName")
    public void setOrganismName(String value) { this.organismName = value; }

    @JsonProperty("Label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabel() { return label; }
    @JsonProperty("Label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("Strain")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStrain() { return strain; }
    @JsonProperty("Strain")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStrain(String value) { this.strain = value; }

    @JsonProperty("Supergroup")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSupergroup() { return supergroup; }
    @JsonProperty("Supergroup")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSupergroup(String value) { this.supergroup = value; }

    @JsonProperty("BiologicalProperties")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BiologicalProperties getBiologicalProperties() { return biologicalProperties; }
    @JsonProperty("BiologicalProperties")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBiologicalProperties(BiologicalProperties value) { this.biologicalProperties = value; }

    @JsonProperty("Organization")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOrganization() { return organization; }
    @JsonProperty("Organization")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganization(String value) { this.organization = value; }

    @JsonProperty("Reproduction")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReproduction() { return reproduction; }
    @JsonProperty("Reproduction")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReproduction(String value) { this.reproduction = value; }

    @JsonProperty("RepliconSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PurpleRepliconSet getRepliconSet() { return repliconSet; }
    @JsonProperty("RepliconSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRepliconSet(PurpleRepliconSet value) { this.repliconSet = value; }

    @JsonProperty("Ploidy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Ploidy getPloidy() { return ploidy; }
    @JsonProperty("Ploidy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPloidy(Ploidy value) { this.ploidy = value; }

    @JsonProperty("Count")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Count> getCount() { return count; }
    @JsonProperty("Count")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCount(List<Count> value) { this.count = value; }

    @JsonProperty("Replicon")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReplicon() { return replicon; }
    @JsonProperty("Replicon")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReplicon(String value) { this.replicon = value; }
}
