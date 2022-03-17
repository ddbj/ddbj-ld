package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class XrefLink {
    @XmlElement(name = "DB")
    @JsonProperty("DB")
    private String db;

    @XmlElement(name = "ID")
    @JsonProperty("ID")
    private String id;

    @XmlElement(name = "LABEL")
    @JsonProperty("LABEL")
    private String label;
}
