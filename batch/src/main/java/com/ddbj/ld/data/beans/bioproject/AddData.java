package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class AddData {
    private String targetDB;
    private String targetDBContext;
    private Data data;

    @JsonProperty("target_db")
    public String getTargetDB() { return targetDB; }
    @JsonProperty("target_db")
    public void setTargetDB(String value) { this.targetDB = value; }

    @JsonProperty("target_db_context")
    public String getTargetDBContext() { return targetDBContext; }
    @JsonProperty("target_db_context")
    public void setTargetDBContext(String value) { this.targetDBContext = value; }

    @JsonProperty("Data")
    public Data getData() { return data; }
    @JsonProperty("Data")
    public void setData(Data value) { this.data = value; }
}
