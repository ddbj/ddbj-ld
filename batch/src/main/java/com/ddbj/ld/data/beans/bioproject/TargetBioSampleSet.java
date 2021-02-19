package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

public class TargetBioSampleSet {
    private List<String> id;

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getID() { return id; }
    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(List<String> value) { this.id = value; }
}
