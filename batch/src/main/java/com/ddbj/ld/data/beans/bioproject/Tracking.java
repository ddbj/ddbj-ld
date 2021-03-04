package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Tracking {
    private Record record;

    @JsonProperty("Record")
<<<<<<< HEAD
    public Record getRecord() { return record; }
    @JsonProperty("Record")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Record getRecord() { return record; }
    @JsonProperty("Record")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setRecord(Record value) { this.record = value; }
}
