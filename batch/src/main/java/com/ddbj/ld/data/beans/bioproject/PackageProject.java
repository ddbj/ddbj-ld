package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

// ProjectLinksは無視
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PackageProject {
    @XmlElement(name="Project")
    @JsonProperty("Project")
    private ProjectProject project;

    @XmlElement(name="Submission")
    @JsonProperty("Submission")
    private Submission submission;
}
