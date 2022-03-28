package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = CommonDeserializers.IDDeserializer.class)
@Data
public class ID {
    @XmlAttribute(name = "label")
    @JsonProperty("label")
    private String label;

    @XmlAttribute(name = "namespace")
    @JsonProperty("namespace")
    private String namespace;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
