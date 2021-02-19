package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Descriptor {
    private String studyTitle;
    private StudyTypes studyTypes;
    private String studyAbstract;
    private String centerProjectName;
    private RelatedStudies relatedStudies;

    @JsonProperty("STUDY_TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStudyTitle() { return studyTitle; }
    @JsonProperty("STUDY_TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyTitle(String value) { this.studyTitle = value; }

    @JsonProperty("STUDY_TYPES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyTypes getStudyTypes() { return studyTypes; }
    @JsonProperty("STUDY_TYPES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyTypes(StudyTypes value) { this.studyTypes = value; }

    @JsonProperty("STUDY_ABSTRACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStudyAbstract() { return studyAbstract; }
    @JsonProperty("STUDY_ABSTRACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyAbstract(String value) { this.studyAbstract = value; }

    @JsonProperty("CENTER_PROJECT_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCenterProjectName() { return centerProjectName; }
    @JsonProperty("CENTER_PROJECT_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCenterProjectName(String value) { this.centerProjectName = value; }

    @JsonProperty("RELATED_STUDIES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RelatedStudies getRelatedStudies() { return relatedStudies; }
    @JsonProperty("RELATED_STUDIES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelatedStudies(RelatedStudies value) { this.relatedStudies = value; }
}
