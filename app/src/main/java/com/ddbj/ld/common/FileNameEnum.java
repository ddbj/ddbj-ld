package com.ddbj.ld.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO 後でまとめる
@AllArgsConstructor
@Getter
public enum FileNameEnum {
    // XML file name
    BIO_PROJECT_XML("/bioproject/bioproject.xml"),
    SUBMISSION_XML(".submission.xml"),
    ANALYSIS_XML(".analysis.xml"),
    EXPERIMENT_XML(".experiment.xml"),
    BIO_SAMPLE_XML("/biosample/biosample_set.xml"),
    RUN_XML(".run.xml"),
    STUDY_XML(".study.xml"),
    SAMPLE_XML(".sample.xml"),

    // JSON file name
    BIO_PROJECT_JSON("/bioproject/bioproject.json"),
    SUBMISSION_JSON(".submission.json"),
    ANALYSIS_JSON(".analysis.json"),
    EXPERIMENT_JSON(".experiment.json"),
    BIO_SAMPLE_JSON("/biosample/biosample_set.json"),
    RUN_JSON(".run.json"),
    STUDY_JSON(".study.json"),
    SAMPLE_JSON(".sample.json"),

    // TSV file name
    SRA_ACCESSIONS("SRA_Accessions.tab");

    private final String fileName;
}
