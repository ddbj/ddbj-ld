package com.ddbj.ld.bean.jga.dataset;

import com.fasterxml.jackson.annotation.*;

public class DATASETClass {
    private String alias;
    private String centerName;
    private String brokerName;
    private String accession;
    private String dataVersion;
    private String participantSetVersion;
    private String submissionDate;
    private String lastUpdate;
    private Identifiers identifiers;
    private String title;
    private String description;
    private String datasetType;
    private DataRefs dataRefs;
    private AnalysisRefs analysisRefs;
    private Ref policyRef;
    private DatasetLinks datasetLinks;
    private DatasetAttributes datasetAttributes;

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

    @JsonProperty("data_version")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDataVersion() { return dataVersion; }
    @JsonProperty("data_version")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataVersion(String value) { this.dataVersion = value; }

    @JsonProperty("participant_set_version")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getParticipantSetVersion() { return participantSetVersion; }
    @JsonProperty("participant_set_version")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setParticipantSetVersion(String value) { this.participantSetVersion = value; }

    @JsonProperty("submission_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSubmissionDate() { return submissionDate; }
    @JsonProperty("submission_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmissionDate(String value) { this.submissionDate = value; }

    @JsonProperty("last_update")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLastUpdate() { return lastUpdate; }
    @JsonProperty("last_update")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLastUpdate(String value) { this.lastUpdate = value; }

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

    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("DATASET_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDatasetType() { return datasetType; }
    @JsonProperty("DATASET_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDatasetType(String value) { this.datasetType = value; }

    @JsonProperty("DATA_REFS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DataRefs getDataRefs() { return dataRefs; }
    @JsonProperty("DATA_REFS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataRefs(DataRefs value) { this.dataRefs = value; }

    @JsonProperty("ANALYSIS_REFS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AnalysisRefs getAnalysisRefs() { return analysisRefs; }
    @JsonProperty("ANALYSIS_REFS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisRefs(AnalysisRefs value) { this.analysisRefs = value; }

    @JsonProperty("POLICY_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Ref getPolicyRef() { return policyRef; }
    @JsonProperty("POLICY_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPolicyRef(Ref value) { this.policyRef = value; }

    @JsonProperty("DATASET_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DatasetLinks getDatasetLinks() { return datasetLinks; }
    @JsonProperty("DATASET_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDatasetLinks(DatasetLinks value) { this.datasetLinks = value; }

    @JsonProperty("DATASET_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DatasetAttributes getDatasetAttributes() { return datasetAttributes; }
    @JsonProperty("DATASET_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDatasetAttributes(DatasetAttributes value) { this.datasetAttributes = value; }
}
