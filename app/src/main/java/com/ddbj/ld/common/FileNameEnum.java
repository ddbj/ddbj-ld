package com.ddbj.ld.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO 後でまとめる
@AllArgsConstructor
@Getter
public enum FileNameEnum {
    // DRA XML file name
    BIO_PROJECT_XML("/bioproject/bioproject.xml"),
    SUBMISSION_XML(".submission.xml"),
    ANALYSIS_XML(".analysis.xml"),
    EXPERIMENT_XML(".experiment.xml"),
    BIO_SAMPLE_XML("/biosample/biosample_set.xml"),
    RUN_XML(".run.xml"),
    STUDY_XML(".study.xml"),
    SAMPLE_XML(".sample.xml"),

    // TSV file name
    SRA_ACCESSIONS("SRA_Accessions.tab"),

    // JGA XML file name
    JGA_STUDY_XML("JGA_STUDY.xml"),
    DATASET_XML("JGA_DATASET.xml"),
    POLICY_XML("JGA_POLICY.xml"),
    DAC_XML("JGA_DAC.xml"),

    // CSV file name(JGA Relation)
    CSV_FILE("result.csv");

    private final String fileName;
}
