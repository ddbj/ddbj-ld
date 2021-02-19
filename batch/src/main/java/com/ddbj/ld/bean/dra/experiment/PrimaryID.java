package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class PrimaryID {
    private Title label;
    private Title content;

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getLabel() { return label; }
    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(Title value) { this.label = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(Title value) { this.content = value; }
}
