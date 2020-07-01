package com.ddbj.ld.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeEnum {
    BIOPROJECT("bioproject"),
    BIOSAMPLE("biosample"),
    SUBMISSION("dra-submission"),
    ANALYSIS("dra-analysis"),
    EXPERIMENT("dra-experiment"),
    RUN("dra-run"),
    STUDY("dra-study"),
    SAMPLE("dra-sample"),
    JGA_STUDY("jga-study"),
    DATASET("jga-dataset"),
    POLICY("jga-policy"),
    DAC("jga-dac");

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
