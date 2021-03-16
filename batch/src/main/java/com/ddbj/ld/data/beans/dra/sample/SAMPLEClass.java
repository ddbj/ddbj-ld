package com.ddbj.ld.data.beans.dra.sample;

import com.ddbj.ld.data.beans.dra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SAMPLEClass {
    private String alias;
    private String centerName;
    private String brokerName;
    private String accession;
    private Identifiers identifiers;
    private String title;
    private SampleName sampleName;
    private String description;
    private SampleLinks sampleLinks;
    private SampleAttributes sampleAttributes;

    @JsonProperty("alias")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAlias() { return alias; }
    @JsonProperty("alias")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAlias(String value) { this.alias = value; }

    @JsonProperty("center_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCenterName() { return centerName; }
    @JsonProperty("center_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCenterName(String value) { this.centerName = value; }

    @JsonProperty("broker_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBrokerName() { return brokerName; }
    @JsonProperty("broker_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBrokerName(String value) { this.brokerName = value; }

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccession(String value) { this.accession = value; }

    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Identifiers getIdentifiers() { return identifiers; }
    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIdentifiers(Identifiers value) { this.identifiers = value; }

    @JsonProperty("TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTitle() { return title; }
    @JsonProperty("TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("SAMPLE_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SampleName getSampleName() { return sampleName; }
    @JsonProperty("SAMPLE_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSampleName(SampleName value) { this.sampleName = value; }

    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("SAMPLE_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SampleLinks getSampleLinks() { return sampleLinks; }
    @JsonProperty("SAMPLE_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSampleLinks(SampleLinks value) { this.sampleLinks = value; }

    @JsonProperty("SAMPLE_ATTRIBUTES")
    public SampleAttributes getSampleAttributes() { return sampleAttributes; }
    @JsonProperty("SAMPLE_ATTRIBUTES")
    public void setSampleAttributes(SampleAttributes value) { this.sampleAttributes = value; }
}
