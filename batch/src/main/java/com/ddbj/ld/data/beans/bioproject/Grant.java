package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // NCBI独自の項目であるPIを無視
@Data
public class Grant {
    @XmlAttribute(name = "GrantId")
    @JsonProperty("GrantId")
    private String grantID;

    @XmlElement(name = "Title")
    @JsonProperty("Title")
    private String title;

    @XmlElement(name = "Agency")
    @JsonProperty("Agency")
    private Agency agency;
}
