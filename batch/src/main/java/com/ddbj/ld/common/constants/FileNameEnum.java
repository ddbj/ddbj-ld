package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO 後でまとめる
@AllArgsConstructor
@Getter
public enum FileNameEnum {
    // DRA XML file name
    BIOPROJECT_XML("bioproject.xml"),
    SUBMISSION_XML(".submission.xml"),
    ANALYSIS_XML(".analysis.xml"),
    EXPERIMENT_XML(".experiment.xml"),
    BIOSAMPLE_XML("biosample_set.xml"),
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
    ANALYSIS_EXPERIMENT_RELATION("analysis-experiment-relation.csv"),
    ANALYSIS_STUDY_RELATION("analysis-study-relation.csv"),
    DATA_EXPERIMENT_RELATION("data-experiment-relation.csv"),
    DATASET_ANALYSIS_RELATION("dataset-analysis-relation.csv"),
    DATASET_DATA_RELATION("dataset-data-relation.csv"),
    DATASET_POLICY_RELATION("dataset-policy-relation.csv"),
    EXPERIMENT_STUDY_RELATION("experiment-study-relation.csv"),
    POLICY_DAC_RELATION("policy-dac-relation.csv"),

    // JGA Date name
    JGA_DATE("date.csv");

    public final String fileName;
}
