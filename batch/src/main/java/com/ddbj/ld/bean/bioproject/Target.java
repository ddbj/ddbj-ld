package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Target {
    private String sampleScope;
    private String material;
    private String capture;
    private String biosampleID;
    private Organism organism;
    private String provider;
    private String description;

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

    @JsonProperty("biosample_id")
    public String getBiosampleID() { return biosampleID; }
    @JsonProperty("biosample_id")
    public void setBiosampleID(String value) { this.biosampleID = value; }

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
}
