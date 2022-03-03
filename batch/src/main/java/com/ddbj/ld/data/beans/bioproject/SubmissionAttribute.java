package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SubmissionAttribute {
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
