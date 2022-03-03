package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class BiologicalSample {
    @XmlElement(name="CultureSample")
    @JsonProperty("CultureSample")
    private String cultureSample;

    @XmlElement(name="CellSample")
    @JsonProperty("CellSample")
    private String cellSample;

    @XmlElement(name="TissueSample")
    @JsonProperty("TissueSample")
    private String tissueSample;
}
