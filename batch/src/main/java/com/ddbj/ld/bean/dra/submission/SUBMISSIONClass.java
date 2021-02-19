package com.ddbj.ld.bean.dra.submission;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class SUBMISSIONClass {
    private String alias;
    private String centerName;
    private String brokerName;
    private String accession;
    private OffsetDateTime submissionDate;
    private String submissionComment;
    private String labName;
    private Identifiers identifiers;
    private String title;
    private Contacts contacts;
    private Actions actions;
    private SubmissionLinks submissionLinks;
    private SubmissionAttributes submissionAttributes;

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

    @JsonProperty("submission_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getSubmissionDate() { return submissionDate; }
    @JsonProperty("submission_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmissionDate(OffsetDateTime value) { this.submissionDate = value; }

    @JsonProperty("submission_comment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSubmissionComment() { return submissionComment; }
    @JsonProperty("submission_comment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmissionComment(String value) { this.submissionComment = value; }

    @JsonProperty("lab_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabName() { return labName; }
    @JsonProperty("lab_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabName(String value) { this.labName = value; }

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

    @JsonProperty("ACTIONS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Actions getActions() { return actions; }
    @JsonProperty("ACTIONS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setActions(Actions value) { this.actions = value; }

    @JsonProperty("SUBMISSION_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SubmissionLinks getSubmissionLinks() { return submissionLinks; }
    @JsonProperty("SUBMISSION_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmissionLinks(SubmissionLinks value) { this.submissionLinks = value; }

    @JsonProperty("SUBMISSION_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SubmissionAttributes getSubmissionAttributes() { return submissionAttributes; }
    @JsonProperty("SUBMISSION_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmissionAttributes(SubmissionAttributes value) { this.submissionAttributes = value; }
}
