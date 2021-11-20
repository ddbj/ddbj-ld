package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;

// 本アプリとして保持するべきVisibility(!=SRA_Accessions.tab)
@AllArgsConstructor
public enum VisibilityEnum {
    UNRESTRICTED_ACCESS("unrestricted-access"),
    CONTROLLED_ACCESS("controlled-access");

    public final String visibility;
}
