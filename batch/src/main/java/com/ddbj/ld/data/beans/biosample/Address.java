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
public class Address {
    @XmlAttribute(name = "term")
    @JsonProperty("postal_code")
    private String postalCode;

    @XmlElement(name = "Street")
    @JsonProperty("Street")
    private String street;

    @XmlElement(name = "City")
    @JsonProperty("City")
    private String city;

    @XmlElement(name = "Sub")
    @JsonProperty("Sub")
    private String sub;

    @XmlElement(name = "Country")
    @JsonProperty("Country")
    private String country;
}
