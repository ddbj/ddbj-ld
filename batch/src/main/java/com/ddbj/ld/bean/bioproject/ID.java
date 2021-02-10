package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ID {
    private String localID;
    private String userID;
    private String db;
    private String content;

    @JsonProperty("local_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocalID() { return localID; }
    @JsonProperty("local_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocalID(String value) { this.localID = value; }

    @JsonProperty("user_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUserID() { return userID; }
    @JsonProperty("user_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUserID(String value) { this.userID = value; }

    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDB() { return db; }
    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDB(String value) { this.db = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
