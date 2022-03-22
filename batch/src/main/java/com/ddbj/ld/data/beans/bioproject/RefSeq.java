package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RefSeq {
    @XmlAttribute(name = "representation")
    @JsonProperty("representation")
    private String representation;

    @XmlElement(name = "AnnotationSource")
    @JsonProperty("AnnotationSource")
    private Source annotationSource;

    @XmlElement(name = "SequenceSource")
    @JsonProperty("SequenceSource")
    private Source sequenceSource;

    @XmlElement(name = "NomenclatureSource")
    @JsonProperty("NomenclatureSource")
    private Source nomenclatureSource;
}
