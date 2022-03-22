package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StructuredCitation {
    @XmlElement(name = "TITLE")
    @JsonProperty("TITLE")
    private String title;

    @XmlElement(name = "JOURNAL")
    @JsonProperty("JOURNAL")
    private Journal journal;

    @XmlElement(name = "AUTHOR_SET")
    @JsonProperty("AUTHOR_SET")
    private AuthorSet authorSet;
}
