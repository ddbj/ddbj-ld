package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Type {
    @XmlAttribute(name = "location")
    @JsonProperty("location")
    private String location;

    @XmlAttribute(name = "isSingle")
    @JsonProperty("isSingle")
    private String isSingle;

    @XmlAttribute(name = "typeOtherDescr")
    @JsonProperty("typeOtherDescr")
    private String typeOtherDescr;

    @XmlAttribute(name = "locationOtherDescr")
    @JsonProperty("locationOtherDescr")
    private String locationOtherDescr;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
