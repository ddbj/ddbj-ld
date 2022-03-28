package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true) // ProjectLinksは無視
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PackageProject {
    @XmlElement(name = "Project")
    @JsonProperty("Project")
    private ProjectProject project;

    @XmlElement(name = "Submission")
    @JsonProperty("Submission")
    private Submission submission;
}
