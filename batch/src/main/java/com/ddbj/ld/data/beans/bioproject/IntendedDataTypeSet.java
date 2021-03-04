package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IntendedDataTypeSet {
    private List<String> dataType;

    @JsonProperty("DataType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getDataType() { return dataType; }
    @JsonProperty("DataType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataType(List<String> value) { this.dataType = value; }
}
