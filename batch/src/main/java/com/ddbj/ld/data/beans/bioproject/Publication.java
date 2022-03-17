package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.OffsetDateTimeAdapter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.OffsetDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Publication {
    @XmlAttribute(name = "id")
    @JsonProperty("id")
    private String id;

    @XmlAttribute(name = "date")
    @XmlJavaTypeAdapter(OffsetDateTimeAdapter.class)
    @JsonProperty("date")
    private OffsetDateTime date;

    @XmlAttribute(name = "status")
    @JsonProperty("status")
    private String status;

    @XmlElement(name = "Reference")
    @JsonProperty("Reference")
    private String reference;

    @XmlElement(name = "StructuredCitation")
    @JsonProperty("StructuredCitation")
    private StructuredCitation structuredCitation;

    @XmlElement(name = "DbType")
    @JsonProperty("DbType")
    private String dbType;
}
