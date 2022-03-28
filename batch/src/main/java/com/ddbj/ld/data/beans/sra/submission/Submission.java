package com.ddbj.ld.data.beans.sra.submission;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Submission {
    @JsonProperty("SUBMISSION")
    private SUBMISSIONClass submission;
}
