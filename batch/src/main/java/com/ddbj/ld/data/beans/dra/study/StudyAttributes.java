package com.ddbj.ld.data.beans.dra.study;

import com.fasterxml.jackson.annotation.*;

public class StudyAttributes {
    private StudyAttribute studyAttribute;

    @JsonProperty("STUDY_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyAttribute getStudyAttribute() { return studyAttribute; }
    @JsonProperty("STUDY_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyAttribute(StudyAttribute value) { this.studyAttribute = value; }
}
