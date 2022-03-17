package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ContactName {
    @XmlElement(name = "First")
    @JsonProperty("First")
    private String first;

    @XmlElement(name = "Last")
    @JsonProperty("Last")
    private String last;

    @XmlElement(name = "Middle")
    @JsonProperty("Middle")
    private String middle;
}
