package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Table {
    @XmlAttribute(name = "class")
    @JsonProperty("class")
    private String clas;

    @XmlElement(name = "Caption")
    @JsonProperty("Caption")
    private String caption;

    @XmlElement(name = "Header")
    @JsonProperty("Header")
    private Header header;

    @XmlElement(name = "Body")
    @JsonProperty("Body")
    private Body body;
}
