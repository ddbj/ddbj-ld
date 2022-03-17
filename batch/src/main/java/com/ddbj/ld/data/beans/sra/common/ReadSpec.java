package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ReadSpec {
    @XmlElement(name = "READ_INDEX")
    @JsonProperty("READ_INDEX")
    private String readIndex;

    @XmlElement(name = "READ_LABEL")
    @JsonProperty("READ_LABEL")
    private String readLabel;

    @XmlElement(name = "READ_CLASS")
    @JsonProperty("READ_CLASS")
    private String readClass;

    @XmlElement(name = "READ_TYPE")
    @JsonProperty("READ_TYPE")
    private String readType;

    @XmlElement(name = "RELATIVE_ORDER")
    @JsonProperty("RELATIVE_ORDER")
    @JsonDeserialize(using = CommonDeserializers.RelativeOrderDeserializer.class)
    private RelativeOrder relativeOrder;

    @XmlElement(name = "BASE_COORD")
    @JsonProperty("BASE_COORD")
    private String baseCoord;

    @XmlElement(name = "EXPECTED_BASECALL_TABLE")
    @JsonProperty("EXPECTED_BASECALL_TABLE")
    private ExpectedBasecallTable expectedBasecallTable;
}
