package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Author {
    @XmlElement(name = "NAME")
    @JsonProperty("NAME")
    private Name name;

    @XmlElement(name = "CONSORTIUM")
    @JsonProperty("CONSORTIUM")
    private String consortium;
}
