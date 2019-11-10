package com.ddbj.ld.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileNameEnum {
    SUBMISSION(".submission.xml"),
    RUN(".run.xml"),
    ANALYSIS(".analysis.xml"),
    STUDY(".study.xml"),
    EXPERIMENT(".experiment.xml");

    private final String fileName;
}
