package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Submitter {
    @XmlAttribute(name = "account_id")
    @JsonProperty("account_id")
    private String accountID;

    @XmlAttribute(name = "user_name")
    @JsonProperty("user_name")
    private String userName;

    @XmlAttribute(name = "authority")
    @JsonProperty("authority")
    private String authority;

    @XmlElement(name = "Contact")
    @JsonProperty("Contact")
    private Contact contact;
}
