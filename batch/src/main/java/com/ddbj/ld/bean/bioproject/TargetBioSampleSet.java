package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class TargetBioSampleSet {
    private String id;

    @JsonProperty("ID")
    public String getID() { return id; }
    @JsonProperty("ID")
    public void setID(String value) { this.id = value; }
}
