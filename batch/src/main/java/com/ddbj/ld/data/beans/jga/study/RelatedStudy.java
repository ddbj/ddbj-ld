package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RelatedStudy {
    @XmlElement(name = "RELATED_LINK")
    @JsonProperty("RELATED_LINK")
    private Link relatedLink;

    @XmlElement(name = "IS_PRIMARY")
    @JsonProperty("IS_PRIMARY")
    private String isPrimary;
}
