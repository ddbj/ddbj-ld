package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Paired {
    private String nominalLength;
    private String nominalSdev;

    @JsonProperty("NOMINAL_LENGTH")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNominalLength() { return nominalLength; }
    @JsonProperty("NOMINAL_LENGTH")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNominalLength(String value) { this.nominalLength = value; }

    @JsonProperty("NOMINAL_SDEV")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNominalSdev() { return nominalSdev; }
    @JsonProperty("NOMINAL_SDEV")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNominalSdev(String value) { this.nominalSdev = value; }
}
