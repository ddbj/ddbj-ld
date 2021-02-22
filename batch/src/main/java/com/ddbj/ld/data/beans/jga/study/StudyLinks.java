package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class StudyLinks {
    private List<StudyLink> studyLink;

    @JsonProperty("STUDY_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<StudyLink> getStudyLink() { return studyLink; }
    @JsonProperty("STUDY_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyLink(List<StudyLink> value) { this.studyLink = value; }
}
