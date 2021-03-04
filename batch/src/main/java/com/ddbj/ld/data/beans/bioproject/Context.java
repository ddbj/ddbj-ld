package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Context {
    private String db;
    private String localName;
    private String content;

    @JsonProperty("db")
<<<<<<< HEAD
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
=======
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
>>>>>>> 取り込み、修正
    public void setContent(String value) { this.content = value; }
}
