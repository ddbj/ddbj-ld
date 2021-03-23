package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Objectives {
    private ObjectivesData data;

    @JsonProperty("Data")
    public ObjectivesData getData() { return data; }
    @JsonProperty("Data")
    public void setData(ObjectivesData value) { this.data = value; }
}
