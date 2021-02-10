package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Context {
    private String db;
    private String localName;
    private String content;

    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDB() { return db; }
    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDB(String value) { this.db = value; }

    @JsonProperty("local_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocalName() { return localName; }
    @JsonProperty("local_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocalName(String value) { this.localName = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
