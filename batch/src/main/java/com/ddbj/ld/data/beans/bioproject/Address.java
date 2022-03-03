package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Address {
    @XmlAttribute(name="postal_code")
    @JsonProperty("postal_code")
    private String postalCode;

    @XmlElement(name="Street")
    @JsonProperty("Street")
    private String street;

    @XmlElement(name="City")
    @JsonProperty("City")
    private String city;

    @XmlElement(name="Sub")
    @JsonProperty("Sub")
    private String sub;

    @XmlElement(name="Country")
    @JsonProperty("Country")
    private String country;
}
