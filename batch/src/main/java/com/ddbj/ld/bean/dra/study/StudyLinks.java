package com.ddbj.ld.bean.dra.study;

import com.fasterxml.jackson.annotation.*;

public class StudyLinks {
    private StudyLink studyLink;

    @JsonProperty("STUDY_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyLink getStudyLink() { return studyLink; }
    @JsonProperty("STUDY_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyLink(StudyLink value) { this.studyLink = value; }
}
