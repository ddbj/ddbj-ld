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
public class Replicon {
    @XmlAttribute(name = "order")
    @JsonProperty("order")
    private String order;

    @XmlElement(name = "Type")
    @JsonProperty("Type")
    private Type type;

    @XmlElement(name = "Name")
    @JsonProperty("Name")
    private String name;

    @XmlElement(name = "Size")
    @JsonProperty("Size")
    private Size size;

    @XmlElement(name = "Description")
    @JsonProperty("Description")
    private String description;

    @XmlElement(name = "Synonym")
    @JsonProperty("Synonym")
    private String synonym;

    @XmlAttribute(name = "accession")
    @JsonProperty("accession")
    private String accession;

    @XmlAttribute(name = "version")
    @JsonProperty("version")
    private String version;
}
