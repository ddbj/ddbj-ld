package com.ddbj.ld.data.beans.jga.dac;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;

public class DAC implements IPropertiesBean {
    private DACClass dac;

    @JsonProperty("DAC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DACClass getDAC() { return dac; }
    @JsonProperty("DAC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDAC(DACClass value) { this.dac = value; }
}
