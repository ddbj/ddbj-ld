package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class BioSampleSetID {
    private String localID;
    private String userID;
    private String db;
    private String content;

    @JsonProperty("local_id")
    public String getLocalID() { return localID; }
    @JsonProperty("local_id")
    public void setLocalID(String value) { this.localID = value; }

    @JsonProperty("user_id")
    public String getUserID() { return userID; }
    @JsonProperty("user_id")
    public void setUserID(String value) { this.userID = value; }

    @JsonProperty("db")
    public String getDB() { return db; }
    @JsonProperty("db")
    public void setDB(String value) { this.db = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
