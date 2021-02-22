package com.ddbj.ld.data.beans.bioproject;

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
    private List<String> organization;
    private String reproduction;
    private PurpleRepliconSet repliconSet;
    private Ploidy ploidy;
    private List<Count> count;
    private String replicon;
    private String isolateName;

    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Size getGenomeSize() { return genomeSize; }
    @JsonProperty("GenomeSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGenomeSize(Size value) { this.genomeSize = value; }

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

    @JsonProperty("IsolateName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsolateName() { return isolateName; }
    @JsonProperty("IsolateName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIsolateName(String value) { this.isolateName = value; }
}
