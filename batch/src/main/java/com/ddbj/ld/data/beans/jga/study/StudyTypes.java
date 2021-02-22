package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

public class StudyTypes {
    private List<StudyType> studyType;

    @JsonProperty("STUDY_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<StudyType> getStudyType() { return studyType; }
    @JsonProperty("STUDY_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyType(List<StudyType> value) { this.studyType = value; }
}
