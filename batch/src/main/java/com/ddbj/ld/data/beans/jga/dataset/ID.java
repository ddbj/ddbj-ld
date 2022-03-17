package com.ddbj.ld.data.beans.jga.dataset;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ID {
    @XmlAttribute(name = "namespace")
    @JsonProperty("namespace")
    private String namespace;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
