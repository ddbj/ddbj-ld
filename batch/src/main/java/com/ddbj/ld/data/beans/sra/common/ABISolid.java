package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class ABISolid {
    @XmlElement(name = "INSTRUMENT_MODEL")
    @JsonProperty("INSTRUMENT_MODEL")
    private String instrumentModel;
}
