package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Slf4j
// PIを無視
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Grant {
    @XmlAttribute(name="GrantId")
    @JsonProperty("GrantId")
    private String grantID;

    @XmlElement(name="Title")
    @JsonProperty("Title")
    private String title;

    @XmlElement(name="Agency")
    @JsonProperty("Agency")
    private Agency agency;
}
