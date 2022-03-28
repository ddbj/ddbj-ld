package com.ddbj.ld.data.beans.sra.experiment;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Paired {
    @XmlElement(name = "NOMINAL_LENGTH")
    @JsonProperty("NOMINAL_LENGTH")
    private String nominalLength;

    @XmlElement(name = "NOMINAL_SDEV")
    @JsonProperty("NOMINAL_SDEV")
    private String nominalSdev;
}
