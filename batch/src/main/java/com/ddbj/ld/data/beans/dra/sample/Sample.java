package com.ddbj.ld.data.beans.dra.sample;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;

public class Sample implements IPropertiesBean {
    private SAMPLEClass sample;

    @JsonProperty("SAMPLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SAMPLEClass getSample() { return sample; }
    @JsonProperty("SAMPLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSample(SAMPLEClass value) { this.sample = value; }
}
