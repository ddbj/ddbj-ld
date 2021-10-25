package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;

// SRA_Accessions.tabに記載されているvisibility
@AllArgsConstructor
public enum VisibilityEnum {
    PUBLIC("public"),
    CONTROLLED_ACCESS("controlled-access");

    public final String visibility;
}
