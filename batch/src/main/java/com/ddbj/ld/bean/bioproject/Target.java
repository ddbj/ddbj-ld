package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Target {
    private String sampleScope;
    private String material;
    private String capture;
    private String biosampleID;
    private TargetOrganism organism;
    private String provider;
    private String description;

    @JsonProperty("sample_scope")
    public String getSampleScope() { return sampleScope; }
    @JsonProperty("sample_scope")
    public void setSampleScope(String value) { this.sampleScope = value; }

    @JsonProperty("material")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMaterial() { return material; }
    @JsonProperty("material")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMaterial(String value) { this.material = value; }

    @JsonProperty("capture")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCapture() { return capture; }
    @JsonProperty("capture")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCapture(String value) { this.capture = value; }

    @JsonProperty("biosample_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBiosampleID() { return biosampleID; }
    @JsonProperty("biosample_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBiosampleID(String value) { this.biosampleID = value; }

    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public TargetOrganism getOrganism() { return organism; }
    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganism(TargetOrganism value) { this.organism = value; }

    @JsonProperty("Provider")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProvider() { return provider; }
    @JsonProperty("Provider")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProvider(String value) { this.provider = value; }

    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }
}
