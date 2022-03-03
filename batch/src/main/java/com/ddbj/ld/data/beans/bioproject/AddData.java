package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@lombok.Data
public class AddData {
    @XmlAttribute(name="target_db")
    @JsonProperty("target_db")
    private String targetDB;

    @XmlAttribute(name="target_db_context")
    @JsonProperty("target_db_context")
    private String targetDBContext;

    @XmlElement(name="Data")
    @JsonProperty("Data")
    private Data data;
}
