package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = StudyDeserializers.AgencyDeserializer.class)
@Data
public class Agency {
    @XmlAttribute(name = "abbr")
    @JsonProperty("abbr")
    private String abbr;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
