package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Environment {
    private String salinity;
    private String oxygenReq;
    private String optimumTemperature;
    private String temperatureRange;
    private String habitat;

    @JsonProperty("Salinity")
    public String getSalinity() { return salinity; }
    @JsonProperty("Salinity")
    public void setSalinity(String value) { this.salinity = value; }

    @JsonProperty("OxygenReq")
    public String getOxygenReq() { return oxygenReq; }
    @JsonProperty("OxygenReq")
    public void setOxygenReq(String value) { this.oxygenReq = value; }

    @JsonProperty("OptimumTemperature")
    public String getOptimumTemperature() { return optimumTemperature; }
    @JsonProperty("OptimumTemperature")
    public void setOptimumTemperature(String value) { this.optimumTemperature = value; }

    @JsonProperty("TemperatureRange")
    public String getTemperatureRange() { return temperatureRange; }
    @JsonProperty("TemperatureRange")
    public void setTemperatureRange(String value) { this.temperatureRange = value; }

    @JsonProperty("Habitat")
    public String getHabitat() { return habitat; }
    @JsonProperty("Habitat")
    public void setHabitat(String value) { this.habitat = value; }
}
