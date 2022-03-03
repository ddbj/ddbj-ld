package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Group {
    @XmlAttribute(name="groupType")
    @JsonProperty("groupType")
    private String groupType;

    @XmlAttribute(name="label")
    @JsonProperty("label")
    private String label;

    @XmlElement(name="ProjectIDRef")
    @JsonProperty("ProjectIDRef")
    private ProjectIDRef projectIDRef;

    @XmlElement(name="ID")
    @JsonProperty("ID")
    private ID id;

    @XmlElement(name="Description")
    @JsonProperty("Description")
    private String description;
}
