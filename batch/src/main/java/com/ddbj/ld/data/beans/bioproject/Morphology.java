package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Morphology {
    private String gram;
    private String enveloped;
    private List<String> shape;
    private String endospores;
    private String motility;

    @JsonProperty("Gram")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGram() { return gram; }
    @JsonProperty("Gram")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGram(String value) { this.gram = value; }

    @JsonProperty("Enveloped")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEnveloped() { return enveloped; }
    @JsonProperty("Enveloped")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEnveloped(String value) { this.enveloped = value; }

    @JsonProperty("Shape")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getShape() { return shape; }
    @JsonProperty("Shape")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setShape(List<String> value) { this.shape = value; }

    @JsonProperty("Endospores")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEndospores() { return endospores; }
    @JsonProperty("Endospores")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEndospores(String value) { this.endospores = value; }

    @JsonProperty("Motility")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMotility() { return motility; }
    @JsonProperty("Motility")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMotility(String value) { this.motility = value; }
}
