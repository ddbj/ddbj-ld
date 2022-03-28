package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Provider {
    @XmlElement(name = "Id")
    @JsonProperty("Id")
    private String id;

    @XmlElement(name = "Url")
    @JsonProperty("Url")
    private String url;

    @XmlElement(name = "LotId")
    @JsonProperty("LotId")
    private String lotId;

    @XmlElement(name = "Location")
    @JsonProperty("Location")
    private String location;

    @XmlAttribute(name = "provider_name")
    @JsonProperty("provider_name")
    private String providerName;

    @XmlAttribute(name = "preparation_date")
    @JsonProperty("preparation_date")
    private String preparationDate;
}
