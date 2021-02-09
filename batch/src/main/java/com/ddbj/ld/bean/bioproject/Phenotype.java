package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Phenotype {
    private String bioticRelationship;
    private String trophicLevel;
    private String disease;

    @JsonProperty("BioticRelationship")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBioticRelationship() { return bioticRelationship; }
    @JsonProperty("BioticRelationship")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBioticRelationship(String value) { this.bioticRelationship = value; }

    @JsonProperty("TrophicLevel")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTrophicLevel() { return trophicLevel; }
    @JsonProperty("TrophicLevel")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTrophicLevel(String value) { this.trophicLevel = value; }

    @JsonProperty("Disease")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDisease() { return disease; }
    @JsonProperty("Disease")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDisease(String value) { this.disease = value; }
}
