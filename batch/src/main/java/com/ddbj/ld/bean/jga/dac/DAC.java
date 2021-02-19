package com.ddbj.ld.bean.jga.dac;

import com.fasterxml.jackson.annotation.*;

public class DAC {
    private DACClass dac;

    @JsonProperty("DAC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DACClass getDAC() { return dac; }
    @JsonProperty("DAC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDAC(DACClass value) { this.dac = value; }
}
