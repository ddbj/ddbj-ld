package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class ID {
    private Title label;
    private Title namespace;
    private Title content;

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getLabel() { return label; }
    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(Title value) { this.label = value; }

    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getNamespace() { return namespace; }
    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNamespace(Title value) { this.namespace = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(Title value) { this.content = value; }
}
