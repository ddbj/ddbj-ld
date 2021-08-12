package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Method {
    private String methodType;
    private String content;

    @JsonProperty("method_type")
    public String getMethodType() { return methodType; }
    @JsonProperty("method_type")
    public void setMethodType(String value) { this.methodType = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
