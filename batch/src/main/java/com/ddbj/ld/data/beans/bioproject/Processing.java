package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Processing {
    @XmlAttribute(name="owner")
    @JsonProperty("owner")
    private String owner;

    @XmlAttribute(name="id")
    @JsonProperty("id")
    private String id;

    @XmlAttribute(name="action")
    @JsonProperty("action")
    private String action;
}
