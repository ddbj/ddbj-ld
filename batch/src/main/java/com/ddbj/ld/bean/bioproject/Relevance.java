package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Relevance {
    private String agricultural;
    private String medical;
    private String industrial;
    private String environmental;
    private String evolution;
    private String modelOrganism;
    private String other;

    @JsonProperty("Agricultural")
    public String getAgricultural() { return agricultural; }
    @JsonProperty("Agricultural")
    public void setAgricultural(String value) { this.agricultural = value; }

    @JsonProperty("Medical")
    public String getMedical() { return medical; }
    @JsonProperty("Medical")
    public void setMedical(String value) { this.medical = value; }

    @JsonProperty("Industrial")
    public String getIndustrial() { return industrial; }
    @JsonProperty("Industrial")
    public void setIndustrial(String value) { this.industrial = value; }

    @JsonProperty("Environmental")
    public String getEnvironmental() { return environmental; }
    @JsonProperty("Environmental")
    public void setEnvironmental(String value) { this.environmental = value; }

    @JsonProperty("Evolution")
    public String getEvolution() { return evolution; }
    @JsonProperty("Evolution")
    public void setEvolution(String value) { this.evolution = value; }

    @JsonProperty("ModelOrganism")
    public String getModelOrganism() { return modelOrganism; }
    @JsonProperty("ModelOrganism")
    public void setModelOrganism(String value) { this.modelOrganism = value; }

    @JsonProperty("Other")
    public String getOther() { return other; }
    @JsonProperty("Other")
    public void setOther(String value) { this.other = value; }
}
