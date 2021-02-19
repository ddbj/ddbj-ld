package com.ddbj.ld.data.beans.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class Release {
    private String target;

    @JsonProperty("target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTarget() { return target; }
    @JsonProperty("target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTarget(String value) { this.target = value; }
}
