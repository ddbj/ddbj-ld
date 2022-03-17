package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Name {
    @XmlElement(name = "First")
    @JsonProperty("First")
    private String first;

    @XmlElement(name = "Last")
    @JsonProperty("Last")
    private String last;

    @XmlElement(name = "Middle")
    @JsonProperty("Middle")
    private String middle;

    @XmlElement(name = "Suffix")
    @JsonProperty("Suffix")
    private String suffix;
}
