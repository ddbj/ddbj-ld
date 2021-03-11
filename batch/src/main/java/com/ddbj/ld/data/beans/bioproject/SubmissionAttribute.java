package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class SubmissionAttribute {
    private String tag;
    private String value;
    private String units;

    @JsonProperty("TAG")
    public String getTag() { return tag; }
    @JsonProperty("TAG")
    public void setTag(String value) { this.tag = value; }

    @JsonProperty("VALUE")
    public String getValue() { return value; }
    @JsonProperty("VALUE")
    public void setValue(String value) { this.value = value; }

    @JsonProperty("UNITS")
    public String getUnits() { return units; }
    @JsonProperty("UNITS")
    public void setUnits(String value) { this.units = value; }
}
