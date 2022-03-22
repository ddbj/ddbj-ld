package com.ddbj.ld.data.beans.jga.dac;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DAC {
    @XmlElement(name = "DAC")
    @JsonProperty("DAC")
    private DACClass dac;
}
