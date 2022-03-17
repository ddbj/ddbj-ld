package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.common.Deserializers;
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
@Data
public class PackageClass {
    @XmlAttribute(name = "display_name")
    @JsonProperty("display_name")
    @JsonDeserialize(using = Deserializers.StringDeserializer.class)
    private String displayName;

    @XmlValue
    @JsonProperty("content")
    @JsonDeserialize(using = Deserializers.StringDeserializer.class)
    private String content;
}
