package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ProjectAssembly {
    @XmlAttribute(name = "typeOfPairing")
    @JsonProperty("typeOfPairing")
    private String typeOfPairing;

    @XmlElement(name="Assembly")
    @JsonProperty("Assembly")
    private Assembly assembly;

    @XmlElement(name="ProjectIDRef")
    @JsonProperty("ProjectIDRef")
    private ArchiveID projectIDRef;
}
