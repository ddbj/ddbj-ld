package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Release {
    private String target;

    @JsonProperty("target")
    public String getTarget() { return target; }
    @JsonProperty("target")
    public void setTarget(String value) { this.target = value; }
}
