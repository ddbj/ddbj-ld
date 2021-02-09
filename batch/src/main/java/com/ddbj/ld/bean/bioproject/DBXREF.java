package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class DBXREF {
    private String db;
    private List<String> id;

    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDB() { return db; }
    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDB(String value) { this.db = value; }

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getID() { return id; }
    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(List<String> value) { this.id = value; }
}
