package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;

// SRA_Accessions.tabに記載されているstatus
@AllArgsConstructor
public enum StatusEnum {
    LIVE("live"),
    SUPPRESSED("suppressed"),
    UNPUBLISHED("unpublished");

    public final String status;
}
