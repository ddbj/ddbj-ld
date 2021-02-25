package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum XmlTagEnum {
    BIO_PROJECT_START("<Package>"),
    BIO_PROJECT_END("</Package>"),

    JGA_STUDY_START("<STUDY "),
    JGA_STUDY_END("</STUDY>"),
    JGA_DATASET_START("<DATASET "),
    JGA_DATASET_END("</DATASET>"),
    JGA_POLICY_START("<POLICY "),
    JGA_POLICY_END("</POLICY>"),
    JGA_DAC_START("<DAC "),
    JGA_DAC_END("</DAC>");

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
