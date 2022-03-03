package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ExternalLink {
    @XmlAttribute(name="label")
    @JsonProperty("label")
    private String label;

    @XmlAttribute(name="category")
    @JsonProperty("category")
    private String category;

    @XmlElement(name="URL")
    @JsonProperty("URL")
    private String url;

    @XmlElement(name="dbXREF")
    @JsonProperty("dbXREF")
    private DBXREF dbXREF;
}
