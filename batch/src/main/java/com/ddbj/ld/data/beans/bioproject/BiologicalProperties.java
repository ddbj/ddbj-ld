package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class BiologicalProperties {
    @XmlElement(name="Morphology")
    @JsonProperty("Morphology")
    private Morphology morphology;

    @XmlElement(name="BiologicalSample")
    @JsonProperty("BiologicalSample")
    private BiologicalSample biologicalSample;

    @XmlElement(name="Environment")
    @JsonProperty("Environment")
    private Environment environment;

    @XmlElement(name="Phenotype")
    @JsonProperty("Phenotype")
    private Phenotype phenotype;
}
