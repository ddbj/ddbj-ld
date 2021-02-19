package com.ddbj.ld.bean.dra.run;

import com.fasterxml.jackson.annotation.*;

public class ABISolid {
    private String instrumentModel;

    @JsonProperty("INSTRUMENT_MODEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getInstrumentModel() { return instrumentModel; }
    @JsonProperty("INSTRUMENT_MODEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setInstrumentModel(String value) { this.instrumentModel = value; }
}
