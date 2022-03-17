package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SpotDescriptor {
    @XmlElement(name = "SPOT_DECODE_SPEC")
    @JsonProperty("SPOT_DECODE_SPEC")
    private SpotDecodeSpec spotDecodeSpec;
}
