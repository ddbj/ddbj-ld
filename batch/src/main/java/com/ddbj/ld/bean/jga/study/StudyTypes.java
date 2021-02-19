package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class StudyTypes {
    private StudyType studyType;

    @JsonProperty("STUDY_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyType getStudyType() { return studyType; }
    @JsonProperty("STUDY_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyType(StudyType value) { this.studyType = value; }
}
