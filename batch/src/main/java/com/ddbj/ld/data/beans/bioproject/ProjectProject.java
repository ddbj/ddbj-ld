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
public class ProjectProject {
    @XmlElement(name = "ProjectID")
    @JsonProperty("ProjectID")
    private ProjectID projectID;

    @XmlElement(name = "ProjectDescr")
    @JsonProperty("ProjectDescr")
    private ProjectDescr projectDescr;

    @XmlElement(name = "ProjectType")
    @JsonProperty("ProjectType")
    private ProjectType projectType;
}

