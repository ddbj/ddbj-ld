package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class DataTypeSet {
    private String dataType;

    @JsonProperty("DataType")
    public String getDataType() { return dataType; }
    @JsonProperty("DataType")
    public void setDataType(String value) { this.dataType = value; }
}
