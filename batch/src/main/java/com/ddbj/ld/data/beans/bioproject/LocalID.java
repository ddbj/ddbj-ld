package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = BioProjectDeserializers.LocalIDDeserializer.class)
@Data
public class LocalID {
    @XmlAttribute(name = "submission_id")
    @JsonProperty("submission_id")
    private String submissionID;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
