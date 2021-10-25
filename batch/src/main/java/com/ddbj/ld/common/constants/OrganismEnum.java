package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrganismEnum {
    HOMO_SAPIENS("9606", "Homo sapiens");

    public final String identifier;
    public final String name;
}
