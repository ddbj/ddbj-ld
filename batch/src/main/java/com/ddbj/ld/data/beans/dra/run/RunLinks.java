package com.ddbj.ld.data.beans.dra.run;

import com.fasterxml.jackson.annotation.*;

public class RunLinks {
    private RunLink runLink;

    @JsonProperty("RUN_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RunLink getRunLink() { return runLink; }
    @JsonProperty("RUN_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRunLink(RunLink value) { this.runLink = value; }
}
