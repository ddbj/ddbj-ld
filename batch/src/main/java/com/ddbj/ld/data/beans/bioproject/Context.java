package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Context {
    private String db;
    private String localName;
    private String content;

    @JsonProperty("db")
    public String getDB() { return db; }
    @JsonProperty("db")
    public void setDB(String value) { this.db = value; }

    @JsonProperty("local_name")
    public String getLocalName() { return localName; }
    @JsonProperty("local_name")
    public void setLocalName(String value) { this.localName = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
