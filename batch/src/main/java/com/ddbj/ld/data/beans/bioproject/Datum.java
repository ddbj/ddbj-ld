package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Datum {
    private String dataType;
    private String content;

    @JsonProperty("data_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDataType() { return dataType; }
    @JsonProperty("data_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataType(String value) { this.dataType = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
