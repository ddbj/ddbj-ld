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
    SAMPLE("sample"),
    JGA_STUDY("jga_study"),
    DATASET("dataset"),
    POLICY("policy"),
    DAC("dac");

    private final String type;

    public static TypeEnum getType(String type) {
        TypeEnum[] types = TypeEnum.values();
        for (TypeEnum typeEnum : types) {
            if (typeEnum.toString().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
