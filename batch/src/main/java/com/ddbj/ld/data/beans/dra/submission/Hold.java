package com.ddbj.ld.data.beans.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class Hold {
    private String target;
    private String holdUntilDate;

    @JsonProperty("target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTarget() { return target; }
    @JsonProperty("target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTarget(String value) { this.target = value; }

    @JsonProperty("HoldUntilDate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getHoldUntilDate() { return holdUntilDate; }
    @JsonProperty("HoldUntilDate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setHoldUntilDate(String value) { this.holdUntilDate = value; }
}
