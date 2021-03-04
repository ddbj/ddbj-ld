package com.ddbj.ld.data.beans.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class SubmissionAttribute {
    private String tag;
    private String value;
    private String units;

    @JsonProperty("TAG")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTag() { return tag; }
    @JsonProperty("TAG")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTag(String value) { this.tag = value; }

    @JsonProperty("VALUE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getValue() { return value; }
    @JsonProperty("VALUE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setValue(String value) { this.value = value; }

    @JsonProperty("UNITS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUnits() { return units; }
    @JsonProperty("UNITS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUnits(String value) { this.units = value; }
}
