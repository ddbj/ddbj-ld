package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;

public class Body {
    private Header row;

    @JsonProperty("Row")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Header getRow() { return row; }
    @JsonProperty("Row")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRow(Header value) { this.row = value; }
}
