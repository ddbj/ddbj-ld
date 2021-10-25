package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeEnum {
    BIOPROJECT("bioproject"),
    BIOSAMPLE("biosample"),
    SUBMISSION("sra-submission"),
    ANALYSIS("sra-analysis"),
    EXPERIMENT("sra-experiment"),
    RUN("sra-run"),
    STUDY("sra-study"),
    SAMPLE("sra-sample"),
    JGA_STUDY("jga-study"),
    JGA_EXPERIMENT("jga-experiment"),
    JGA_ANALYSIS("jga-analysis"),
    JGA_DATASET("jga-dataset"),
    JGA_DATA("jga-data"),
    JGA_POLICY("jga-policy"),
    JGA_DAC("jga-dac");

    public final String type;

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
