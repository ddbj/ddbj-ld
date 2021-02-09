package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class CenterID {
    private String center;
    private String id;
    private String content;

    @JsonProperty("center")
    public String getCenter() { return center; }
    @JsonProperty("center")
    public void setCenter(String value) { this.center = value; }

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.id = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
