package com.ddbj.ld.data.beans.sra.run;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Run {
    @JsonProperty("RUN")
    private RUNClass run;
}
