package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccessionTypeEnum {
    SUBMISSION("SUBMISSION"),
    EXPERIMENT("EXPERIMENT"),
    ANALYSIS("ANALYSIS"),
    RUN("RUN"),
    STUDY("STUDY"),
    SAMPLE("SAMPLE");

    public final String type;

    public static AccessionTypeEnum getType(String type) {
        AccessionTypeEnum[] types = AccessionTypeEnum.values();
        for (var typeEnum : types) {
            if (typeEnum.toString().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
