package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class File {
    @XmlAttribute(name="path")
    @JsonProperty("path")
    private String path;

    @XmlAttribute(name="md5")
    @JsonProperty("md5")
    private String md5;

    @XmlAttribute(name="crc32")
    @JsonProperty("crc32")
    private String crc32;

    @XmlAttribute(name="content_type")
    @JsonProperty("content_type")
    private String contentType;

    @XmlAttribute(name="target_db_label")
    @JsonProperty("target_db_label")
    private String targetDBLabel;

    @XmlElement(name="Tracking")
    @JsonProperty("Tracking")
    private Tracking tracking;

    @XmlElement(name="DataType")
    @JsonProperty("DataType")
    private String dataType;

    @XmlAttribute(name="target_db")
    @JsonProperty("target_db")
    private String targetDB;

    @XmlAttribute(name="target_db_context")
    @JsonProperty("target_db_context")
    private String targetDBContext;
}
