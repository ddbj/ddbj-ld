package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class BiologicalSample {
    private String cultureSample;
    private String cellSample;
    private String tissueSample;

    @JsonProperty("CultureSample")
<<<<<<< HEAD
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
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCultureSample() { return cultureSample; }
    @JsonProperty("CultureSample")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCultureSample(String value) { this.cultureSample = value; }

    @JsonProperty("CellSample")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCellSample() { return cellSample; }
    @JsonProperty("CellSample")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCellSample(String value) { this.cellSample = value; }

    @JsonProperty("TissueSample")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTissueSample() { return tissueSample; }
    @JsonProperty("TissueSample")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setTissueSample(String value) { this.tissueSample = value; }
}
