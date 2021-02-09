package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class DBXREF {
    private String db;
    private String id;

    @JsonProperty("db")
    public String getDB() { return db; }
    @JsonProperty("db")
    public void setDB(String value) { this.db = value; }

    @JsonProperty("ID")
    public String getID() { return id; }
    @JsonProperty("ID")
    public void setID(String value) { this.id = value; }
}
