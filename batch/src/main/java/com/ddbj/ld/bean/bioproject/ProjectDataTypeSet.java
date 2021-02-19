package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectDataTypeSet {
    private String dataType;
    private String data;

    @JsonProperty("DataType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDataType() { return dataType; }
    @JsonProperty("DataType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataType(String value) { this.dataType = value; }

    @JsonProperty("Data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return data; }
    @JsonProperty("Data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.data = value; }
}