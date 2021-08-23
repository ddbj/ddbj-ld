package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BiologicalProperties {
    private Morphology morphology;
    private BiologicalSample biologicalSample;
    private Environment environment;
    private Phenotype phenotype;

    @JsonProperty("Morphology")
    public Morphology getMorphology() { return morphology; }
    @JsonProperty("Morphology")
    public void setMorphology(Morphology value) { this.morphology = value; }

    @JsonProperty("BiologicalSample")
    public BiologicalSample getBiologicalSample() { return biologicalSample; }
    @JsonProperty("BiologicalSample")
    public void setBiologicalSample(BiologicalSample value) { this.biologicalSample = value; }

    @JsonProperty("Environment")
    public Environment getEnvironment() { return environment; }
    @JsonProperty("Environment")
    public void setEnvironment(Environment value) { this.environment = value; }

    @JsonProperty("Phenotype")
    public Phenotype getPhenotype() { return phenotype; }
    @JsonProperty("Phenotype")
    public void setPhenotype(Phenotype value) { this.phenotype = value; }
}
