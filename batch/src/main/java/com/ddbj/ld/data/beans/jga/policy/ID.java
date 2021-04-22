package com.ddbj.ld.data.beans.jga.policy;

import com.fasterxml.jackson.annotation.*;

public class ID {
    private String namespace;
    private String content;

    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNamespace() { return namespace; }
    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNamespace(String value) { this.namespace = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
