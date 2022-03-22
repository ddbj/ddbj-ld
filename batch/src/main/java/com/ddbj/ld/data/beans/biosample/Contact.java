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
public class Contact {
    @XmlAttribute(name = "email")
    @JsonProperty("email")
    private String email;

    @XmlAttribute(name = "phone")
    @JsonProperty("phone")
    private String phone;

    @XmlAttribute(name = "fax")
    @JsonProperty("fax")
    private String fax;

    @XmlAttribute(name = "lab")
    @JsonProperty("lab")
    private String lab;

    @XmlAttribute(name = "department")
    @JsonProperty("department")
    private String department;

    @XmlElement(name = "Address")
    @JsonProperty("Address")
    private Address address;

    @XmlElement(name = "Name")
    @JsonProperty("Name")
    private ContactName name;
}
