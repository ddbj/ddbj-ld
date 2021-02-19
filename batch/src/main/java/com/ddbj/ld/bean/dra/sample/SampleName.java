package com.ddbj.ld.bean.dra.sample;

import com.fasterxml.jackson.annotation.*;

public class SampleName {
    private String taxonID;
    private String scientificName;
    private String commonName;
    private String anonymizedName;
    private String individualName;

    @JsonProperty("TAXON_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTaxonID() { return taxonID; }
    @JsonProperty("TAXON_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTaxonID(String value) { this.taxonID = value; }

    @JsonProperty("SCIENTIFIC_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getScientificName() { return scientificName; }
    @JsonProperty("SCIENTIFIC_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setScientificName(String value) { this.scientificName = value; }

    @JsonProperty("COMMON_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCommonName() { return commonName; }
    @JsonProperty("COMMON_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCommonName(String value) { this.commonName = value; }

    @JsonProperty("ANONYMIZED_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAnonymizedName() { return anonymizedName; }
    @JsonProperty("ANONYMIZED_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnonymizedName(String value) { this.anonymizedName = value; }

    @JsonProperty("INDIVIDUAL_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIndividualName() { return individualName; }
    @JsonProperty("INDIVIDUAL_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIndividualName(String value) { this.individualName = value; }
}
