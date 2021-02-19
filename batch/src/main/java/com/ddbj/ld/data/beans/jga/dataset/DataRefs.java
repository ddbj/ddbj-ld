package com.ddbj.ld.data.beans.jga.dataset;

import com.fasterxml.jackson.annotation.*;

public class DataRefs {
    private Ref dataRef;

    @JsonProperty("DATA_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Ref getDataRef() { return dataRef; }
    @JsonProperty("DATA_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataRef(Ref value) { this.dataRef = value; }
}
