package com.ddbj.ld.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum XmlTagEnum {
    JGA_STUDY_SET("STUDY_SET"),
    JGA_STUDY("STUDY"),
    DATASET_SET("DATASET_SET"),
    DATASET("DATASET"),
    POLICY_SET("POLICY_SET"),
    POLICY("POLICY"),
    DAC_SET("DAC_SET"),
    DAC("DAC");

    private final String item;

    public static XmlTagEnum getItem(String item) {
        XmlTagEnum[] Items = XmlTagEnum.values();
        for (XmlTagEnum organismEnum : Items) {
            if (organismEnum.toString().equals(item)) {
                return organismEnum;
            }
        }
        return null;
    }
}
