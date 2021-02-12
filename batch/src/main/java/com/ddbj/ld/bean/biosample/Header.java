package com.ddbj.ld.bean.biosample;

import com.fasterxml.jackson.annotation.*;

public class Header {
    private String cell;

    @JsonProperty("Cell")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCell() { return cell; }
    @JsonProperty("Cell")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCell(String value) { this.cell = value; }
}
