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

    SRA_ANALYSIS ("<ANALYSIS ","</ANALYSIS>"),
    SRA_EXPERIMENT ("<EXPERIMENT ","</EXPERIMENT>"),
    SRA_RUN ("<RUN ","</RUN>"),
    SRA_SUBMISSION ("<SUBMISSION ", "</SUBMISSION>"),
    SRA_SAMPLE ("<SAMPLE ", "</SAMPLE>"),
    SRA_STUDY ("<STUDY ", "</STUDY>");

    public final String start;
    public final String end;
}
