package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Source {
    @XmlElement(name = "Name")
    @JsonProperty("Name")
    private String name;

    @XmlElement(name = "Url")
    @JsonProperty("Url")
    private String url;
}
