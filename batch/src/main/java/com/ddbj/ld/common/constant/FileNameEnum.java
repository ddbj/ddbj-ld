package com.ddbj.ld.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO 後でまとめる
@AllArgsConstructor
@Getter
public enum FileNameEnum {
    // DRA XML file name
    BIOPROJECT_XML("/bioproject/bioproject.xml"),
    SUBMISSION_XML(".submission.xml"),
    ANALYSIS_XML(".analysis.xml"),
    EXPERIMENT_XML(".experiment.xml"),
    BIOSAMPLE_XML("/biosample/biosample_set.xml"),
    RUN_XML(".run.xml"),
    STUDY_XML(".study.xml"),
    SAMPLE_XML(".sample.xml"),

    // SRA_ACCESSIONS name
    SRA_ACCESSIONS("SRA_Accessions.tab"),

    // JGA XML file name
    JGA_STUDY_XML("jga-study.xml"),
    DATASET_XML("jga-dataset.xml"),
    POLICY_XML("jga-policy.xml"),
    DAC_XML("jga-dac.xml"),

    // JGA Relation name
    JGA_RELATION("relation.txt"),

    // JGA Date name
    JGA_DATE("date.txt");

    private final String fileName;
}
