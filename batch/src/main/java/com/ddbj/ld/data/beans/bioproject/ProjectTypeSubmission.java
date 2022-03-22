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
public class ProjectTypeSubmission {
    @XmlElement(name = "Target")
    @JsonProperty("Target")
    private ProjectTypeSubmissionTarget target;

    @XmlElement(name = "TargetBioSampleSet")
    @JsonProperty("TargetBioSampleSet")
    private TargetBioSampleSet targetBioSampleSet;

    @XmlElement(name = "Method")
    @JsonProperty("Method")
    private Method method;

    @XmlElement(name = "Objectives")
    @JsonProperty("Objectives")
    private Objectives objectives;

    @XmlElement(name = "IntendedDataTypeSet")
    @JsonProperty("IntendedDataTypeSet")
    private DataTypeSet intendedDataTypeSet;

    @XmlElement(name = "ProjectDataTypeSet")
    @JsonProperty("ProjectDataTypeSet")
    private DataTypeSet projectDataTypeSet;
}
