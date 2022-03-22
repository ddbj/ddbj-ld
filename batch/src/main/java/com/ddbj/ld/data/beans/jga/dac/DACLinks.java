package com.ddbj.ld.data.beans.jga.dac;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DACLinks {
    @XmlElement(name = "DAC_LINK")
    @JsonProperty("DAC_LINK")
    private DACLink dacLink;
}
