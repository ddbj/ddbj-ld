package com.ddbj.ld.data.beans.dra.study;

import com.fasterxml.jackson.annotation.*;

public class Descriptor {
    private String studyTitle;
    private StudyType studyType;
    private String studyAbstract;
    private String centerProjectName;
    private RelatedStudies relatedStudies;
    private String studyDescription;

    @JsonProperty("STUDY_TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStudyTitle() { return studyTitle; }
    @JsonProperty("STUDY_TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyTitle(String value) { this.studyTitle = value; }

    @JsonProperty("STUDY_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyType getStudyType() { return studyType; }
    @JsonProperty("STUDY_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyType(StudyType value) { this.studyType = value; }

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

    @JsonProperty("STUDY_DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStudyDescription() { return studyDescription; }
    @JsonProperty("STUDY_DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyDescription(String value) { this.studyDescription = value; }
}
