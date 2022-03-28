package com.ddbj.ld.data.beans.jga.policy;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Policy {
    @JsonProperty("POLICY")
    private POLICYClass policy;
}
