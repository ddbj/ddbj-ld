package com.ddbj.ld.data.beans.sra.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Descriptor {
    @XmlElement(name = "STUDY_TITLE")
    @JsonProperty("STUDY_TITLE")
    private String studyTitle;

    @XmlElement(name = "STUDY_TYPE")
    @JsonProperty("STUDY_TYPE")
    private StudyType studyType;

    @XmlElement(name = "STUDY_ABSTRACT")
    @JsonProperty("STUDY_ABSTRACT")
    private String studyAbstract;

    @XmlElement(name = "CENTER_PROJECT_NAME")
    @JsonProperty("CENTER_PROJECT_NAME")
    private String centerProjectName;

    @XmlElement(name = "RELATED_STUDIES")
    @JsonProperty("RELATED_STUDIES")
    private RelatedStudies relatedStudies;

    @XmlElement(name = "STUDY_DESCRIPTION")
    @JsonProperty("STUDY_DESCRIPTION")
    private String studyDescription;
}
