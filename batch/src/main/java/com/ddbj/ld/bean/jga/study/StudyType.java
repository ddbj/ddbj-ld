package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class StudyType {
    private String existingStudyType;
    private String newStudyType;

    @JsonProperty("existing_study_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getExistingStudyType() { return existingStudyType; }
    @JsonProperty("existing_study_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExistingStudyType(String value) { this.existingStudyType = value; }

    @JsonProperty("new_study_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNewStudyType() { return newStudyType; }
    @JsonProperty("new_study_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNewStudyType(String value) { this.newStudyType = value; }
}
