package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Objectives {
    private Data data;

    @JsonProperty("Data")
    public Data getData() { return data; }
    @JsonProperty("Data")
    public void setData(Data value) { this.data = value; }
}
