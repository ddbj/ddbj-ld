package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class To {
    private String content;
    private String id;

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() { return id; }
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setId(String value) { this.id = value; }
}
