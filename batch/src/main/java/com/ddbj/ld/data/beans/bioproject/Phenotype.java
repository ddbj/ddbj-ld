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
public class Phenotype {
    @XmlElement(name = "BioticRelationship")
    @JsonProperty("BioticRelationship")
    private String bioticRelationship;

    @XmlElement(name = "TrophicLevel")
    @JsonProperty("TrophicLevel")
    private String trophicLevel;

    @XmlElement(name = "Disease")
    @JsonProperty("Disease")
    private String disease;
}
