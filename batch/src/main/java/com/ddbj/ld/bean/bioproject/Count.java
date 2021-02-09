package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Count {
    private String repliconType;
    private String content;

    @JsonProperty("repliconType")
    public String getRepliconType() { return repliconType; }
    @JsonProperty("repliconType")
    public void setRepliconType(String value) { this.repliconType = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
