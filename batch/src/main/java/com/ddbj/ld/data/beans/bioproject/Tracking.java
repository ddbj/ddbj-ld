package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Tracking {
    private Record record;

    @JsonProperty("Record")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Record getRecord() { return record; }
    @JsonProperty("Record")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRecord(Record value) { this.record = value; }
}
