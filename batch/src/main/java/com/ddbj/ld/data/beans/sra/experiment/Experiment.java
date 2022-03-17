package com.ddbj.ld.data.beans.sra.experiment;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Experiment {
    @JsonProperty("EXPERIMENT")
    private EXPERIMENTClass experiment;
}
