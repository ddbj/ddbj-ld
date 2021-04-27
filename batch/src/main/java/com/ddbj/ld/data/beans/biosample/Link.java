package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
    private String required;
    private String target;
    private String label;
    private String type; // FIXME
    private String content;


    @JsonProperty("required")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRequired() { return required; }
    @JsonProperty("required")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRequired(String value) { this.required = value; }

    @JsonProperty("target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTarget() { return target; }
    @JsonProperty("target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTarget(String value) { this.target = value; }

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabel() { return label; }
    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(String value) { this.label = value; }

    // TODO: 実データに存在するがXSDに定義が無いため要仕様確認
    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getType() { return type; }
    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setType(String value) { this.type = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
