package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class IntendedDataTypeSet {
    private String dataType;
    // FIXME XSDにはないが、他のTypeSetにはあり、実データにもあるものはあったので暫定対応
    private String data;

    @JsonProperty("DataType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDataType() { return dataType; }
    @JsonProperty("DataType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataType(String value) { this.dataType = value; }
    @JsonProperty("Data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getData() { return data; }
    @JsonProperty("Data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setData(String value) { this.data = value; }
}
