package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PackageSubmission {
    @XmlElement(name = "Submission")
    @JsonProperty("Submission")
    private SubmissionSubmission submission;

    @XmlElement(name = "ProjectAssembly")
    @JsonProperty("ProjectAssembly")
    private SubmissionProjectAssembly projectAssembly;

    @XmlElement(name = "ProjectSubmission")
    @JsonProperty("ProjectSubmission")
    private String projectSubmission;

    @XmlElement(name = "ProjectLinks")
    @JsonProperty("ProjectLinks")
    private ProjectLinks projectLinks;

    @XmlElement(name = "ProjectPresentation")
    @JsonProperty("ProjectPresentation")
    private String projectPresentation;
}
