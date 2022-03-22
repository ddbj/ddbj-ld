package com.ddbj.ld.data.beans.sra.submission;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Action {
    @XmlElement(name = "ADD")
    @JsonProperty("ADD")
    private Add add;

    @XmlElement(name = "MODIFY")
    @JsonProperty("MODIFY")
    private Add modify;

    @XmlElement(name = "SUPPRESS")
    @JsonProperty("SUPPRESS")
    private Release suppress;

    @XmlElement(name = "HOLD")
    @JsonProperty("HOLD")
    private Hold hold;

    @XmlElement(name = "RELEASE")
    @JsonProperty("RELEASE")
    private Release release;

    @XmlElement(name = "VALIDATE")
    @JsonProperty("VALIDATE")
    private Add validate;
}
