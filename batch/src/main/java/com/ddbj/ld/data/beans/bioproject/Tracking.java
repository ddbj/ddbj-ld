package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tracking {
    private Record record;

    @JsonProperty("Record")
    public Record getRecord() { return record; }
    @JsonProperty("Record")
    public void setRecord(Record value) { this.record = value; }
}
