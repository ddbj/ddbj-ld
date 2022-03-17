package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Study {
    @JsonProperty("STUDY")
    private STUDYClass study;
}
