package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;


@JsonIgnoreProperties(ignoreUnknown=true)
public class ABISolid {
    private String instrumentModel;

    @JsonProperty("INSTRUMENT_MODEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getInstrumentModel() { return instrumentModel; }
    @JsonProperty("INSTRUMENT_MODEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setInstrumentModel(String value) { this.instrumentModel = value; }
}
