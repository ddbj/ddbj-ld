package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Contact {
    @XmlAttribute(name="email")
    @JsonProperty("email")
    private String email;

    @XmlAttribute(name="phone")
    @JsonProperty("phone")
    private String phone;

    @XmlAttribute(name="fax")
    @JsonProperty("fax")
    private String fax;

    @XmlElement(name="Address")
    @JsonProperty("Address")
    private Address address;

    @XmlElement(name="Name")
    @JsonProperty("Name")
    private ContactName name;
}
