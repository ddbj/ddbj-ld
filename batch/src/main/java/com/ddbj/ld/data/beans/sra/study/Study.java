package com.ddbj.ld.data.beans.sra.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Study {
    @JsonProperty("STUDY")
    private STUDYClass study;
}
