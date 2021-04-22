package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;

public class ID {
    private String namespace;
    private String otherNamespace;
    private String isPrimary;
    private String content;

    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNamespace() { return namespace; }
    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNamespace(String value) { this.namespace = value; }

    @JsonProperty("other_namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOtherNamespace() { return otherNamespace; }
    @JsonProperty("other_namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOtherNamespace(String value) { this.otherNamespace = value; }

    @JsonProperty("is_primary")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsPrimary() { return isPrimary; }
    @JsonProperty("is_primary")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIsPrimary(String value) { this.isPrimary = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}