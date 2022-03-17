package com.ddbj.ld.data.beans.jga.dataset;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Dataset {
    @JsonProperty("DATASET")
    private DATASETClass dataset;
}
