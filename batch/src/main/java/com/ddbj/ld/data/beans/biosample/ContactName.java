package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
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
