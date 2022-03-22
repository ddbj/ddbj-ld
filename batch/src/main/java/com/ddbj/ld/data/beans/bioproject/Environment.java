package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Environment {
    @XmlElement(name = "Salinity")
    @JsonProperty("Salinity")
    private String salinity;

    @XmlElement(name = "OxygenReq")
    @JsonProperty("OxygenReq")
    private String oxygenReq;

    @XmlElement(name = "OptimumTemperature")
    @JsonProperty("OptimumTemperature")
    private String optimumTemperature;

    @XmlElement(name = "TemperatureRange")
    @JsonProperty("TemperatureRange")
    private String temperatureRange;

    @XmlElement(name = "Habitat")
    @JsonProperty("Habitat")
    private String habitat;
}
