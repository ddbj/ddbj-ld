package com.ddbj.ld.data.beans.jga.dac;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Contact {
    @XmlAttribute(name = "name")
    @JsonProperty("name")
    private String name;

    @XmlAttribute(name = "email")
    @JsonProperty("email")
    private String email;

    @XmlAttribute(name = "telephone_number")
    @JsonProperty("telephone_number")
    private String telephoneNumber;

    @XmlAttribute(name = "organisation")
    @JsonProperty("organisation")
    private String organisation;
}
