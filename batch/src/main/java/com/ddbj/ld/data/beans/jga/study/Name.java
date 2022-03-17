package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Name {
    @XmlElement(name = "FIRST")
    @JsonProperty("FIRST")
    private String first;

    @XmlElement(name = "LAST")
    @JsonProperty("LAST")
    private String last;

    @XmlElement(name = "MIDDLE")
    @JsonProperty("MIDDLE")
    private String middle;

    @XmlElement(name = "SUFFIX")
    @JsonProperty("SUFFIX")
    private String suffix;
}
