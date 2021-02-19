package com.ddbj.ld.bean.jga.dac;

import com.fasterxml.jackson.annotation.*;

public class DACAttributes {
    private DACAttribute dacAttribute;

    @JsonProperty("DAC_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DACAttribute getDACAttribute() { return dacAttribute; }
    @JsonProperty("DAC_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDACAttribute(DACAttribute value) { this.dacAttribute = value; }
}
