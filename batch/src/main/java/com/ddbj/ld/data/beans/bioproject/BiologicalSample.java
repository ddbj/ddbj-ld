package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BiologicalSample {
    private String cultureSample;
    private String cellSample;
    private String tissueSample;

    @JsonProperty("CultureSample")
    public String getCultureSample() { return cultureSample; }
    @JsonProperty("CultureSample")
    public void setCultureSample(String value) { this.cultureSample = value; }

    @JsonProperty("CellSample")
    public String getCellSample() { return cellSample; }
    @JsonProperty("CellSample")
    public void setCellSample(String value) { this.cellSample = value; }

    @JsonProperty("TissueSample")
    public String getTissueSample() { return tissueSample; }
    @JsonProperty("TissueSample")
    public void setTissueSample(String value) { this.tissueSample = value; }
}
