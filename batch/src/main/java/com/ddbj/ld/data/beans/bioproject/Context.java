package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Context {
    @XmlAttribute(name="db")
    private String db;
    @XmlAttribute(name="local_name")
    private String localName;
    @XmlValue
    private String content;

    @JsonProperty("db")
    public String getDB() { return db; }
    @JsonProperty("db")
    public void setDB(String value) { this.db = value; }

    @JsonProperty("local_name")
    public String getLocalName() { return localName; }
    @JsonProperty("local_name")
    public void setLocalName(String value) { this.localName = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
