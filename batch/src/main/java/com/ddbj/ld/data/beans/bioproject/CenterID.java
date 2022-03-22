package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CenterID {
    @XmlAttribute(name = "center")
    @JsonProperty("center")
    private String center;

    @XmlAttribute(name = "id")
    @JsonProperty("id")
    private String id;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
