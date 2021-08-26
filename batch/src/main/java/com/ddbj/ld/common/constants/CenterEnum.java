package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CenterEnum {
    DDBJ("DDBJ"),
    NCBI("NCBI"),
    EBI("EBI");

    public String center;
}
