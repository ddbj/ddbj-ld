package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Add {
    @XmlAttribute(name="source")
    @JsonProperty("source")
    private String source;

    @XmlAttribute(name="schema")
    @JsonProperty("schema")
    private String schema;
}
