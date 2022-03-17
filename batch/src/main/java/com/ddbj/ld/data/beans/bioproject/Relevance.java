package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = BioProjectDeserializers.RelevanceDeserializer.class)
@Data
public class Relevance {
    @XmlElement(name = "Agricultural")
    @JsonProperty("Agricultural")
    private String agricultural;

    @XmlElement(name = "Medical")
    @JsonProperty("Medical")
    private String medical;

    @XmlElement(name = "Industrial")
    @JsonProperty("Industrial")
    private String industrial;

    @XmlElement(name = "Environmental")
    @JsonProperty("Environmental")
    private String environmental;

    @XmlElement(name = "Evolution")
    @JsonProperty("Evolution")
    private String evolution;

    @XmlElement(name = "ModelOrganism")
    @JsonProperty("ModelOrganism")
    private String modelOrganism;

    @XmlElement(name = "Other")
    @JsonProperty("Other")
    private String other;
}
