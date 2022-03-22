package com.ddbj.ld.data.beans.sra.submission;

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

    @XmlAttribute(name = "inform_on_status")
    @JsonProperty("inform_on_status")
    private String informOnStatus;

    @XmlAttribute(name = "inform_on_error")
    @JsonProperty("inform_on_error")
    private String informOnError;
}
