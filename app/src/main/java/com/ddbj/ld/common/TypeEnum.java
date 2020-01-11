package com.ddbj.ld.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeEnum {
    BIO_PROJECT("bioproject"),
    SUBMISSION("submission"),
    ANALYSIS("analysis"),
    EXPERIMENT("experiment"),
    BIO_SAMPLE("biosample"),
    RUN("run"),
    STUDY("study"),
    SAMPLE("sample");

    private final String type;
}
