package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = BioSampleDeserializers.NameElementDeserializer.class)
@Data
public class NameElement {
    @XmlAttribute(name = "abbreviation")
    @JsonProperty("abbreviation")
    private String abbreviation;

    @XmlAttribute(name = "url")
    @JsonProperty("url")
    private String url;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
