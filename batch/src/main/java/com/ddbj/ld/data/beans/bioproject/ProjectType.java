package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ProjectType {
    @XmlElement(name="ProjectTypeTopSingleOrganism")
    @JsonProperty("ProjectTypeTopSingleOrganism")
    private ProjectTypeTopSingleOrganism projectTypeTopSingleOrganism;

    @XmlElement(name="ProjectTypeTopAdmin")
    @JsonProperty("ProjectTypeTopAdmin")
    private ProjectTypeTopAdmin projectTypeTopAdmin;

    @XmlElement(name="ProjectTypeSubmission")
    @JsonProperty("ProjectTypeSubmission")
    private ProjectTypeSubmission projectTypeSubmission;
}
