package com.ddbj.ld.data.beans.jga.dataset;

import com.fasterxml.jackson.annotation.*;

public class Dataset {
    private DATASETClass dataset;

    @JsonProperty("DATASET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DATASETClass getDataset() { return dataset; }
    @JsonProperty("DATASET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataset(DATASETClass value) { this.dataset = value; }
}
