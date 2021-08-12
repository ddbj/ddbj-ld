package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TargetBioSampleSet {
    private String id;

    @JsonProperty("ID")
    public String getID() { return id; }
    @JsonProperty("ID")
    public void setID(String value) { this.id = value; }
}
