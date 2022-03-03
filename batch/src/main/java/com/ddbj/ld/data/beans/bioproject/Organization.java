package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Organization {
    @XmlAttribute(name = "type")
    @JsonProperty("type")
    private String type;

    @XmlAttribute(name = "role")
    @JsonProperty("role")
    private String role;

    @XmlAttribute(name = "org_id")
    @JsonProperty("org_id")
    private String orgID;

    @XmlAttribute(name = "url")
    @JsonProperty("url")
    private String url;

    @XmlElement(name = "Name")
    @JsonProperty("Name")
    private OrganizationName name;

    // 個人情報なので消そうとしたが、NCBI、DDBJともに公開XMLには記載されていないため問題ない
    @XmlElement(name = "Contact")
    @JsonProperty("Contact")
    private Contact contact;
}
