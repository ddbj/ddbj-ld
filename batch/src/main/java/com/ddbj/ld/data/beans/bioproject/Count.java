package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Count {
    @XmlAttribute(name = "repliconType")
    @JsonProperty("repliconType")
    private String repliconType;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
