package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Analysis {
    @JsonProperty("ANALYSIS")
    private ANALYSISClass analysis;
}
