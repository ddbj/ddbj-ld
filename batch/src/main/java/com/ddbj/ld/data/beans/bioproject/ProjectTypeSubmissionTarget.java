package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectTypeSubmissionTarget {
    private String sampleScope;
    private String material;
    private String capture;
    private Organism organism;
    private String provider;
    private String description;
    private BioSampleSet bioSampleSet;

    @JsonProperty("sample_scope")
    public String getSampleScope() { return sampleScope; }
    @JsonProperty("sample_scope")
    public void setSampleScope(String value) { this.sampleScope = value; }

    @JsonProperty("material")
    public String getMaterial() { return material; }
    @JsonProperty("material")
    public void setMaterial(String value) { this.material = value; }

    @JsonProperty("capture")
    public String getCapture() { return capture; }
    @JsonProperty("capture")
    public void setCapture(String value) { this.capture = value; }

    @JsonProperty("Organism")
    public Organism getOrganism() { return organism; }
    @JsonProperty("Organism")
    public void setOrganism(Organism value) { this.organism = value; }

    @JsonProperty("Provider")
    public String getProvider() { return provider; }
    @JsonProperty("Provider")
    public void setProvider(String value) { this.provider = value; }

    @JsonProperty("Description")
    public String getDescription() { return description; }
    @JsonProperty("Description")
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("BioSampleSet")
    public BioSampleSet getBioSampleSet() { return bioSampleSet; }
    @JsonProperty("BioSampleSet")
    public void setBioSampleSet(BioSampleSet value) { this.bioSampleSet = value; }
}
