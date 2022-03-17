package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserTerm {
    @XmlAttribute(name = "term")
    @JsonProperty("term")
    private String term;

    @XmlAttribute(name = "category")
    @JsonProperty("category")
    private String category;

    @XmlAttribute(name = "units")
    @JsonProperty("units")
    private String units;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
