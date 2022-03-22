package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Grant {
    @XmlAttribute(name = "grant_id")
    @JsonProperty("grant_id")
    private String grantID;

    @XmlElement(name = "TITLE")
    @JsonProperty("TITLE")
    private String title;

    @XmlElement(name = "AGENCY")
    @JsonProperty("AGENCY")
    private Agency agency;
}
