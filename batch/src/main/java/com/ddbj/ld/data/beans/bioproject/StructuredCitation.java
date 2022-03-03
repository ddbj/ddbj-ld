package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class StructuredCitation {
    @XmlElement(name = "Title")
    @JsonProperty("Title")
    private String title;

    @XmlElement(name = "Journal")
    @JsonProperty("Journal")
    private Journal journal;

    @XmlElement(name = "AuthorSet")
    @JsonProperty("AuthorSet")
    private AuthorSet authorSet;
}
