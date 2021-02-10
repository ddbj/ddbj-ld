package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

public class TargetBioSampleSet {
    private List<String> id;

    @JsonProperty("ID")
    public List<String> getID() { return id; }
    @JsonProperty("ID")
    public void setID(List<String> value) { this.id = value; }
}
