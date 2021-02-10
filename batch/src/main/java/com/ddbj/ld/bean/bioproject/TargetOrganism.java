package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class TargetOrganism {
    private String taxID;
    private String species;
    private String organismName;
    private String label;
    private String strain;
    private String replicon;
    private String supergroup;
    private BiologicalProperties biologicalProperties;
    private String organization;
    private String reproduction;
    private FluffyRepliconSet repliconSet;
    private Size genomeSize;

    @JsonProperty("taxID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTaxID() { return taxID; }
    @JsonProperty("taxID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTaxID(String value) { this.taxID = value; }

    @JsonProperty("species")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSpecies() { return species; }
    @JsonProperty("species")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSpecies(String value) { this.species = value; }

    @JsonProperty("OrganismName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOrganismName() { return organismName; }
    @JsonProperty("OrganismName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonProperty("Replicon")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReplicon() { return replicon; }
    @JsonProperty("Replicon")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReplicon(String value) { this.replicon = value; }

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
    public FluffyRepliconSet getRepliconSet() { return repliconSet; }
    @JsonProperty("RepliconSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRepliconSet(FluffyRepliconSet value) { this.repliconSet = value; }

    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Size getGenomeSize() { return genomeSize; }
    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGenomeSize(Size value) { this.genomeSize = value; }
}
