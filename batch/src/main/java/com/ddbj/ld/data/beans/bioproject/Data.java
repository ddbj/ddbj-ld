package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@lombok.Data
public class Data {
    @XmlAttribute(name="name")
    @JsonProperty("name")
    private String name;

    @XmlAttribute(name="data_model")
    @JsonProperty("data_model")
    private String dataModel;

    @XmlAttribute(name="content_type")
    @JsonProperty("content_type")
    private String contentType;

    @XmlAttribute(name="content_encoding")
    @JsonProperty("content_encoding")
    private String contentEncoding;

    @XmlAttribute(name="target_db_label")
    @JsonProperty("target_db_label")
    private String targetDBLabel;

    @XmlElement(name="Tracking")
    @JsonProperty("Tracking")
    private Tracking tracking;

    @XmlElement(name="XmlContent")
    @JsonProperty("XmlContent")
    private XMLContent xmlContent;

    @XmlElement(name="DataContent")
    @JsonProperty("DataContent")
    private String dataContent;
}
