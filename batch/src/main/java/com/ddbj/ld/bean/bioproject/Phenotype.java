package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Phenotype {
    private String bioticRelationship;
    private String trophicLevel;
    private String disease;

    @JsonProperty("BioticRelationship")
    public String getBioticRelationship() { return bioticRelationship; }
    @JsonProperty("BioticRelationship")
    public void setBioticRelationship(String value) { this.bioticRelationship = value; }

    @JsonProperty("TrophicLevel")
    public String getTrophicLevel() { return trophicLevel; }
    @JsonProperty("TrophicLevel")
    public void setTrophicLevel(String value) { this.trophicLevel = value; }

    @JsonProperty("Disease")
    public String getDisease() { return disease; }
    @JsonProperty("Disease")
    public void setDisease(String value) { this.disease = value; }
}
