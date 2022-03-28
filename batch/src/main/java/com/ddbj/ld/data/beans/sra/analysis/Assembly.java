package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Assembly {
    @XmlElement(name = "STANDARD")
    @JsonProperty("STANDARD")
    @JsonDeserialize(using = AnalysisDeserializers.StandardDeserializer.class)
    private Standard standard;

    @XmlElement(name = "CUSTOM")
    @JsonProperty("CUSTOM")
    @JsonDeserialize(using = AnalysisDeserializers.CustomDeserializer.class)
    private Custom custom;
}
