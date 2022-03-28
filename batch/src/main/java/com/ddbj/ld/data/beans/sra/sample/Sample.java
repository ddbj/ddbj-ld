package com.ddbj.ld.data.beans.sra.sample;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Sample {
    @JsonProperty("SAMPLE")
    private SAMPLEClass sample;
}
