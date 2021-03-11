package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Morphology {
    private String gram;
    private String enveloped;
    private String shape;
    private String endospores;
    private String motility;

    @JsonProperty("Gram")
    public String getGram() { return gram; }
    @JsonProperty("Gram")
    public void setGram(String value) { this.gram = value; }

    @JsonProperty("Enveloped")
    public String getEnveloped() { return enveloped; }
    @JsonProperty("Enveloped")
    public void setEnveloped(String value) { this.enveloped = value; }

    @JsonProperty("Shape")
    public String getShape() { return shape; }
    @JsonProperty("Shape")
    public void setShape(String value) { this.shape = value; }

    @JsonProperty("Endospores")
    public String getEndospores() { return endospores; }
    @JsonProperty("Endospores")
    public void setEndospores(String value) { this.endospores = value; }

    @JsonProperty("Motility")
    public String getMotility() { return motility; }
    @JsonProperty("Motility")
    public void setMotility(String value) { this.motility = value; }
}
