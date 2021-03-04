package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Method {
    private String methodType;
    private String content;

    @JsonProperty("method_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMethodType() { return methodType; }
    @JsonProperty("method_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMethodType(String value) { this.methodType = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
