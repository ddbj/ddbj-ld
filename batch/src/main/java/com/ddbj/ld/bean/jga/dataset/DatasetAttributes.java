package com.ddbj.ld.bean.jga.dataset;

import com.fasterxml.jackson.annotation.*;

public class DatasetAttributes {
    private DatasetAttribute datasetAttribute;

    @JsonProperty("DATASET_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DatasetAttribute getDatasetAttribute() { return datasetAttribute; }
    @JsonProperty("DATASET_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDatasetAttribute(DatasetAttribute value) { this.datasetAttribute = value; }
}
