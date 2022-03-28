package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ExpectedBasecallTable {
    @XmlAttribute(name = "default_length")
    @JsonProperty("default_length")
    private String defaultLength;

    @XmlAttribute(name = "base_coord")
    @JsonProperty("base_coord")
    private String baseCoord;

    @XmlElement(name = "BASECALL")
    @JsonProperty("BASECALL")
    @JsonDeserialize(using = CommonDeserializers.BasecallListDeserializer.class)
    private List<Basecall> basecall;
}
