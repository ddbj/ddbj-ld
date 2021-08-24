package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum XmlTagEnum {
    BIO_PROJECT_START("<Package>"),
    BIO_PROJECT_END("</Package>"),

    BIO_SAMPLE_START("<BioSample "),
    BIO_SAMPLE_END("</BioSample>"),

    JGA_STUDY_START("<STUDY "),
    JGA_STUDY_END("</STUDY>"),
    JGA_DATASET_START("<DATASET "),
    JGA_DATASET_END("</DATASET>"),
    JGA_POLICY_START("<POLICY "),
    JGA_POLICY_END("</POLICY>"),
    JGA_DAC_START("<DAC "),
    JGA_DAC_END("</DAC>"),

    DRA_ANALYSIS_START("<ANALYSIS "),
    DRA_ANALYSIS_END("</ANALYSIS>"),
    DRA_EXPERIMENT_START("<EXPERIMENT "),
    DRA_EXPERIMENT_END("</EXPERIMENT>"),
    DRA_RUN_START("<RUN "),
    DRA_RUN_END("</RUN>"),
    DRA_SUBMISSION_START("<SUBMISSION "),
    DRA_SUBMISSION_END("</SUBMISSION>"),
    DRA_SAMPLE_START("<SAMPLE "),
    DRA_SAMPLE_END("</SAMPLE>"),
    DRA_STUDY_START("<STUDY "),
    DRA_STUDY_END("</STUDY>");

    public final String item;

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
