package com.ddbj.ld.data.beans.jga.dataset;

import com.fasterxml.jackson.annotation.*;

public class DatasetLinks {
    private DatasetLink datasetLink;

    @JsonProperty("DATASET_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DatasetLink getDatasetLink() { return datasetLink; }
    @JsonProperty("DATASET_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDatasetLink(DatasetLink value) { this.datasetLink = value; }
}
