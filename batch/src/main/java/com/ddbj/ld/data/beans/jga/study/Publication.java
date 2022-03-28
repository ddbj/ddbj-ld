package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Publication {
    @XmlAttribute(name = "id")
    @JsonProperty("id")
    private String id;

    @XmlAttribute(name = "date")
    @JsonProperty("date")
    private LocalDate date;

    @XmlAttribute(name = "status")
    @JsonProperty("status")
    private String status;

    @XmlElement(name = "REFERENCE")
    @JsonProperty("REFERENCE")
    private String reference;

    @XmlElement(name = "STRUCTURED_CITATION")
    @JsonProperty("STRUCTURED_CITATION")
    private StructuredCitation structuredCitation;

    @XmlElement(name = "DB_TYPE")
    @JsonProperty("DB_TYPE")
    private String dbType;
}
