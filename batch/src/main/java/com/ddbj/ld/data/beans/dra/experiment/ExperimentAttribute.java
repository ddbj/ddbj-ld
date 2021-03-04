package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class ExperimentAttribute {
    private Title tag;
    private Title value;
    private Title units;

    @JsonProperty("TAG")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getTag() { return tag; }
    @JsonProperty("TAG")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTag(Title value) { this.tag = value; }

    @JsonProperty("VALUE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getValue() { return value; }
    @JsonProperty("VALUE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setValue(Title value) { this.value = value; }

    @JsonProperty("UNITS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getUnits() { return units; }
    @JsonProperty("UNITS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUnits(Title value) { this.units = value; }
}
