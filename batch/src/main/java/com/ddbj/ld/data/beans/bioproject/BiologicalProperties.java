package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class BiologicalProperties {
    private Morphology morphology;
    private BiologicalSample biologicalSample;
    private Environment environment;
    private Phenotype phenotype;

    @JsonProperty("Morphology")
<<<<<<< HEAD
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
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Morphology getMorphology() { return morphology; }
    @JsonProperty("Morphology")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMorphology(Morphology value) { this.morphology = value; }

    @JsonProperty("BiologicalSample")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BiologicalSample getBiologicalSample() { return biologicalSample; }
    @JsonProperty("BiologicalSample")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBiologicalSample(BiologicalSample value) { this.biologicalSample = value; }

    @JsonProperty("Environment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Environment getEnvironment() { return environment; }
    @JsonProperty("Environment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEnvironment(Environment value) { this.environment = value; }

    @JsonProperty("Phenotype")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Phenotype getPhenotype() { return phenotype; }
    @JsonProperty("Phenotype")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setPhenotype(Phenotype value) { this.phenotype = value; }
}
