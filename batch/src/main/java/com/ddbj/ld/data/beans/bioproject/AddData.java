package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class AddData {
    private String targetDB;
    private String targetDBContext;
    private Data data;

    @JsonProperty("target_db")
<<<<<<< HEAD
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
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTargetDB() { return targetDB; }
    @JsonProperty("target_db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTargetDB(String value) { this.targetDB = value; }

    @JsonProperty("target_db_context")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTargetDBContext() { return targetDBContext; }
    @JsonProperty("target_db_context")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTargetDBContext(String value) { this.targetDBContext = value; }

    @JsonProperty("Data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Data getData() { return data; }
    @JsonProperty("Data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setData(Data value) { this.data = value; }
}
