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
@JsonDeserialize(using = CommonDeserializers.PrimaryIDDeserializer.class)
@Data
public class PrimaryID {
    @XmlAttribute(name = "label")
    @JsonProperty("label")
    private String label;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
