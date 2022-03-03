package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SubmissionProjectAssembly {
    @XmlElement(name = "ProjectAssembly")
    @JsonProperty("ProjectAssembly")
    private ProjectAssembly projectAssembly;
}
