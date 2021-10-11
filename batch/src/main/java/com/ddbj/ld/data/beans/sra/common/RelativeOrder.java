package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;

public class RelativeOrder {
    private String followsReadIndex;
    private String precedesReadIndex;

    @JsonProperty("follows_read_index")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFollowsReadIndex() { return followsReadIndex; }
    @JsonProperty("follows_read_index")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFollowsReadIndex(String value) { this.followsReadIndex = value; }

    @JsonProperty("precedes_read_index")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPrecedesReadIndex() { return precedesReadIndex; }
    @JsonProperty("precedes_read_index")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPrecedesReadIndex(String value) { this.precedesReadIndex = value; }
}
