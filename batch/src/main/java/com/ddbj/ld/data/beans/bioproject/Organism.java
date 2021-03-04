package com.ddbj.ld.data.beans.bioproject;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.*;
=======
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
>>>>>>> 取り込み、修正

public class Organism {
    private String taxID;
    private String species;
    private String organismName;
    private String label;
    private String strain;
<<<<<<< HEAD
    private String isolateName;
    private String breed;
    private String cultivar;
    private String supergroup;
    private BiologicalProperties biologicalProperties;
    private String organization;
=======
    private String supergroup;
    private BiologicalProperties biologicalProperties;
    private List<String> organization;
>>>>>>> 取り込み、修正
    private String reproduction;
    private RepliconSet repliconSet;
    private Size genomeSize;

    @JsonProperty("taxID")
<<<<<<< HEAD
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
    public String getLabel() { return label; }
    @JsonProperty("Label")
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("Strain")
    public String getStrain() { return strain; }
    @JsonProperty("Strain")
    public void setStrain(String value) { this.strain = value; }

    @JsonProperty("IsolateName")
    public String getIsolateName() { return isolateName; }
    @JsonProperty("IsolateName")
    public void setIsolateName(String value) { this.isolateName = value; }

    @JsonProperty("Breed")
    public String getBreed() { return breed; }
    @JsonProperty("Breed")
    public void setBreed(String value) { this.breed = value; }

    @JsonProperty("Cultivar")
    public String getCultivar() { return cultivar; }
    @JsonProperty("Cultivar")
    public void setCultivar(String value) { this.cultivar = value; }

    @JsonProperty("Supergroup")
    public String getSupergroup() { return supergroup; }
    @JsonProperty("Supergroup")
    public void setSupergroup(String value) { this.supergroup = value; }

    @JsonProperty("BiologicalProperties")
    public BiologicalProperties getBiologicalProperties() { return biologicalProperties; }
    @JsonProperty("BiologicalProperties")
    public void setBiologicalProperties(BiologicalProperties value) { this.biologicalProperties = value; }

    @JsonProperty("Organization")
    public String getOrganization() { return organization; }
    @JsonProperty("Organization")
    public void setOrganization(String value) { this.organization = value; }

    @JsonProperty("Reproduction")
    public String getReproduction() { return reproduction; }
    @JsonProperty("Reproduction")
    public void setReproduction(String value) { this.reproduction = value; }

    @JsonProperty("RepliconSet")
    public RepliconSet getRepliconSet() { return repliconSet; }
    @JsonProperty("RepliconSet")
    public void setRepliconSet(RepliconSet value) { this.repliconSet = value; }

    @JsonProperty("GenomeSize")
    public Size getGenomeSize() { return genomeSize; }
    @JsonProperty("GenomeSize")
=======
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
    public List<String> getOrganization() { return organization; }
    @JsonProperty("Organization")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganization(List<String> value) { this.organization = value; }

    @JsonProperty("Reproduction")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReproduction() { return reproduction; }
    @JsonProperty("Reproduction")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReproduction(String value) { this.reproduction = value; }

    @JsonProperty("RepliconSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RepliconSet getRepliconSet() { return repliconSet; }
    @JsonProperty("RepliconSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRepliconSet(RepliconSet value) { this.repliconSet = value; }

    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Size getGenomeSize() { return genomeSize; }
    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setGenomeSize(Size value) { this.genomeSize = value; }
}
