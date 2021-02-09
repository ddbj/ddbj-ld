package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Data {
    private String dataType;
    private String content;

    @JsonProperty("data_type")
    public String getDataType() { return dataType; }
    @JsonProperty("data_type")
    public void setDataType(String value) { this.dataType = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
