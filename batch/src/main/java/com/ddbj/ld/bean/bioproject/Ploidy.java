package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Ploidy {
    private String type;
    private String content;

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
