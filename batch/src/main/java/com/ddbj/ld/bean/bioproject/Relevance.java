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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAgricultural() { return agricultural; }
    @JsonProperty("Agricultural")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAgricultural(String value) { this.agricultural = value; }

    @JsonProperty("Medical")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMedical() { return medical; }
    @JsonProperty("Medical")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMedical(String value) { this.medical = value; }

    @JsonProperty("Industrial")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIndustrial() { return industrial; }
    @JsonProperty("Industrial")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIndustrial(String value) { this.industrial = value; }

    @JsonProperty("Environmental")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEnvironmental() { return environmental; }
    @JsonProperty("Environmental")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEnvironmental(String value) { this.environmental = value; }

    @JsonProperty("Evolution")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEvolution() { return evolution; }
    @JsonProperty("Evolution")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEvolution(String value) { this.evolution = value; }

    @JsonProperty("ModelOrganism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getModelOrganism() { return modelOrganism; }
    @JsonProperty("ModelOrganism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setModelOrganism(String value) { this.modelOrganism = value; }

    @JsonProperty("Other")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOther() { return other; }
    @JsonProperty("Other")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOther(String value) { this.other = value; }
}
