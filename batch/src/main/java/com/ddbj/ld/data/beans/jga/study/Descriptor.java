package com.ddbj.ld.data.beans.jga.study;

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

    @XmlElement(name = "STUDY_TYPES")
    @JsonProperty("STUDY_TYPES")
    private StudyTypes studyTypes;

    @XmlElement(name = "STUDY_ABSTRACT")
    @JsonProperty("STUDY_ABSTRACT")
    private String studyAbstract;

    @XmlElement(name = "CENTER_PROJECT_NAME")
    @JsonProperty("CENTER_PROJECT_NAME")
    private String centerProjectName;

    @XmlElement(name = "RELATED_STUDIES")
    @JsonProperty("RELATED_STUDIES")
    private RelatedStudies relatedStudies;
}
