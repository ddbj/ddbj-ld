package com.ddbj.ld.data.beans.jga.dac;

import com.fasterxml.jackson.annotation.*;

public class DACClass {
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
    private Contacts contacts;
    private DACLinks dacLinks;
    private DACAttributes dacAttributes;

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

    @JsonProperty("CONTACTS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Contacts getContacts() { return contacts; }
    @JsonProperty("CONTACTS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContacts(Contacts value) { this.contacts = value; }

    @JsonProperty("DAC_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DACLinks getDACLinks() { return dacLinks; }
    @JsonProperty("DAC_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDACLinks(DACLinks value) { this.dacLinks = value; }

    @JsonProperty("DAC_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DACAttributes getDACAttributes() { return dacAttributes; }
    @JsonProperty("DAC_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDACAttributes(DACAttributes value) { this.dacAttributes = value; }
}
