package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;

public class Organism {
    private String taxonomyID;
    private String role;
    private String organismName;
    private String label;
    private String isolateName;
    private String breed;
    private String strain;
    private String cultivar;

    @JsonProperty("taxonomy_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTaxonomyID() { return taxonomyID; }
    @JsonProperty("taxonomy_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTaxonomyID(String value) { this.taxonomyID = value; }

    @JsonProperty("role")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRole() { return role; }
    @JsonProperty("role")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRole(String value) { this.role = value; }

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

    @JsonProperty("IsolateName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsolateName() { return isolateName; }
    @JsonProperty("IsolateName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIsolateName(String value) { this.isolateName = value; }

    @JsonProperty("Breed")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBreed() { return breed; }
    @JsonProperty("Breed")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBreed(String value) { this.breed = value; }

    @JsonProperty("Strain")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStrain() { return strain; }
    @JsonProperty("Strain")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStrain(String value) { this.strain = value; }

    @JsonProperty("Cultivar")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCultivar() { return cultivar; }
    @JsonProperty("Cultivar")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCultivar(String value) { this.cultivar = value; }
}
