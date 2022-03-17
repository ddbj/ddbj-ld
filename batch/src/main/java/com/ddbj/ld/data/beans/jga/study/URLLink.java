package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class URLLink {
    @XmlElement(name = "LABEL")
    @JsonProperty("LABEL")
    private String label;

    @XmlElement(name = "URL")
    @JsonProperty("URL")
    private String url;
}
