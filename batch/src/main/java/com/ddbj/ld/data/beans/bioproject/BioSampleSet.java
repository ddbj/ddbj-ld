package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class BioSampleSet {
    private BioSampleSetID id;

    @JsonProperty("ID")
    public BioSampleSetID getID() { return id; }
    @JsonProperty("ID")
    public void setID(BioSampleSetID value) { this.id = value; }
}
