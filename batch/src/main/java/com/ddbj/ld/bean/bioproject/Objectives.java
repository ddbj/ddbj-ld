package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Objectives {
    private List<Datum> data;

    @JsonProperty("Data")
    public List<Datum> getData() { return data; }
    @JsonProperty("Data")
    public void setData(List<Datum> value) { this.data = value; }
}
