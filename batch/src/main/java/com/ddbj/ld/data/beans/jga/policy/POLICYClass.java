package com.ddbj.ld.data.beans.jga.policy;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;

public class POLICYClass implements IPropertiesBean {
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
    private DACRef dacRef;
    private String policyText;
    private String policyFile;
    private PolicyLinks policyLinks;
    private PolicyAttributes policyAttributes;

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

    @JsonProperty("DAC_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DACRef getDACRef() { return dacRef; }
    @JsonProperty("DAC_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDACRef(DACRef value) { this.dacRef = value; }

    @JsonProperty("POLICY_TEXT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPolicyText() { return policyText; }
    @JsonProperty("POLICY_TEXT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPolicyText(String value) { this.policyText = value; }

    @JsonProperty("POLICY_FILE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPolicyFile() { return policyFile; }
    @JsonProperty("POLICY_FILE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPolicyFile(String value) { this.policyFile = value; }

    @JsonProperty("POLICY_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PolicyLinks getPolicyLinks() { return policyLinks; }
    @JsonProperty("POLICY_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPolicyLinks(PolicyLinks value) { this.policyLinks = value; }

    @JsonProperty("POLICY_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PolicyAttributes getPolicyAttributes() { return policyAttributes; }
    @JsonProperty("POLICY_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPolicyAttributes(PolicyAttributes value) { this.policyAttributes = value; }
}
