package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.common.Deserializers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true) // NCBI submission管理用内部ID. submissionidをskip
@Data
public class Link {
    @XmlAttribute(name = "target")
    @JsonProperty("target")
    private String target;

    @XmlAttribute(name = "label")
    @JsonProperty("label")
    private String label;

    @XmlAttribute(name = "type")
    @JsonProperty("type")
    private String type;

    @XmlValue
    @JsonProperty("content")
    @JsonDeserialize(using = Deserializers.StringDeserializer.class)
    private String content;
}
