package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BioSample implements IPropertiesBean {
    private BioSampleClass biosample;

    @JsonProperty("BioSample")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BioSampleClass getBioSample() { return biosample; }
    @JsonProperty("BioSample")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBioSample(BioSampleClass value) { this.biosample = value; }
}
