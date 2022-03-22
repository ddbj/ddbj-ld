package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // biosample_idを無視
@Data
public class ProjectTypeSubmissionTarget {
    @XmlAttribute(name = "sample_scope")
    @JsonProperty("sample_scope")
    private String sampleScope;

    @XmlAttribute(name = "material")
    @JsonProperty("material")
    private String material;

    @XmlAttribute(name = "capture")
    @JsonProperty("capture")
    private String capture;

    @XmlElement(name = "Organism")
    @JsonProperty("Organism")
    private BioProjectOrganism organism;

    @XmlElement(name = "Provider")
    @JsonProperty("Provider")
    private String provider;

    @XmlElement(name = "Description")
    @JsonProperty("Description")
    private String description;

    @XmlElement(name = "BioSampleSet")
    @JsonProperty("BioSampleSet")
    private BioSampleSet bioSampleSet;
}
