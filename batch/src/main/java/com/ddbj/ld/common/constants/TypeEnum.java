package com.ddbj.ld.common.constants;

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
    JGA_EXPERIMENT("jga-experiment"),
    JGA_ANALYSIS("jga-analysis"),
    JGA_DATASET("jga-dataset"),
    JGA_DATA("jga-data"),
    JGA_POLICY("jga-policy"),
    JGA_DAC("jga-dac");

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

    // FIXME DRAの処理を直したら、getTypeと統合する
    public static TypeEnum getTypeByArgs(String type) {
        TypeEnum[] types = TypeEnum.values();
        for (TypeEnum typeEnum : types) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
