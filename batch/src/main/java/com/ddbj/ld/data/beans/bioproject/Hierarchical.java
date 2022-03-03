package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Hierarchical {
    @XmlAttribute(name="type")
    @JsonProperty("type")
    private String type;

    @XmlElement(name="MemberID")
    @JsonProperty("MemberID")
    private ProjectIDRef memberID;
}
