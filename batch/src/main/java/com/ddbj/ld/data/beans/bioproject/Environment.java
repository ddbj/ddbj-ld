package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Environment {
    private String salinity;
    private String oxygenReq;
    private String optimumTemperature;
    private String temperatureRange;
    private String habitat;

    @JsonProperty("Salinity")
<<<<<<< HEAD
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
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSalinity() { return salinity; }
    @JsonProperty("Salinity")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSalinity(String value) { this.salinity = value; }

    @JsonProperty("OxygenReq")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOxygenReq() { return oxygenReq; }
    @JsonProperty("OxygenReq")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOxygenReq(String value) { this.oxygenReq = value; }

    @JsonProperty("OptimumTemperature")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOptimumTemperature() { return optimumTemperature; }
    @JsonProperty("OptimumTemperature")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOptimumTemperature(String value) { this.optimumTemperature = value; }

    @JsonProperty("TemperatureRange")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTemperatureRange() { return temperatureRange; }
    @JsonProperty("TemperatureRange")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTemperatureRange(String value) { this.temperatureRange = value; }

    @JsonProperty("Habitat")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getHabitat() { return habitat; }
    @JsonProperty("Habitat")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setHabitat(String value) { this.habitat = value; }
}
