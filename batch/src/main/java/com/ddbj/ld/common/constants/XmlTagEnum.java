package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum XmlTagEnum {
    BIO_PROJECT ("<Package>", "</Package>"),

    BIO_SAMPLE ("<BioSample ", "</BioSample>"),

    JGA_STUDY ("<STUDY ", "</STUDY>"),
    JGA_DATASET ("<DATASET ", "</DATASET>"),
    JGA_POLICY ("<POLICY ", "</POLICY>"),
    JGA_DAC ("<DAC ","</DAC>"),

    DRA_ANALYSIS ("<ANALYSIS ","</ANALYSIS>"),
    DRA_EXPERIMENT ("<EXPERIMENT ","</EXPERIMENT>"),
    DRA_RUN ("<RUN ","</RUN>"),
    DRA_SUBMISSION ("<SUBMISSION ", "</SUBMISSION>"),
    DRA_SAMPLE ("<SAMPLE ", "</SAMPLE>"),
    DRA_STUDY ("<STUDY ", "</STUDY>");

    public final String start;
    public final String end;
}
