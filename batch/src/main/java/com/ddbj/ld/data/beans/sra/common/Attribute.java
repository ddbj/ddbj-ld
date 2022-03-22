package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = CommonDeserializers.AttributeDeserializer.class)
@Data
public class Attribute {
    @XmlElement(name = "TAG")
    @JsonProperty("TAG")
    private String tag;

    @XmlElement(name = "VALUE")
    @JsonProperty("VALUE")
    private String value;

    @XmlElement(name = "UNITS")
    @JsonProperty("UNITS")
    private String units;
}
