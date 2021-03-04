package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Count {
    private String repliconType;
    private String content;

    @JsonProperty("repliconType")
<<<<<<< HEAD
    public String getRepliconType() { return repliconType; }
    @JsonProperty("repliconType")
    public void setRepliconType(String value) { this.repliconType = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRepliconType() { return repliconType; }
    @JsonProperty("repliconType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRepliconType(String value) { this.repliconType = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setContent(String value) { this.content = value; }
}
