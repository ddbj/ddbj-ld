package com.ddbj.ld.bean.jga.dac;

import com.fasterxml.jackson.annotation.*;

public class DACLinks {
    private DACLink dacLink;

    @JsonProperty("DAC_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DACLink getDACLink() { return dacLink; }
    @JsonProperty("DAC_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDACLink(DACLink value) { this.dacLink = value; }
}
