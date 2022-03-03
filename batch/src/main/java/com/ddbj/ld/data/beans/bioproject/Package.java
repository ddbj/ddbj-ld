package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// ArchiveID, Submissionを無視
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name="Package")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Package implements IPropertiesBean {
    @XmlElement(name="Processing")
    @JsonProperty("Processing")
    private Processing processing;

    @XmlElement(name="Project")
    @JsonProperty("Project")
    private PackageProject project;
}
