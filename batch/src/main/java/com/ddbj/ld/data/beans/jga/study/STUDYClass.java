package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;

public class STUDYClass {
    private String alias;
    private String centerName;
    private String brokerName;
    private String accession;
    private String dataVersion;
    private String participantSetVersion;
    private String submissionDate;
    private String lastUpdate;
    private Identifiers identifiers;
    private Descriptor descriptor;
    private Grants grants;
    private Publications publications;
    private StudyLinks studyLinks;
    private StudyAttributes studyAttributes;

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

    @JsonProperty("DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Descriptor getDescriptor() { return descriptor; }
    @JsonProperty("DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescriptor(Descriptor value) { this.descriptor = value; }

    @JsonProperty("GRANTS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Grants getGrants() { return grants; }
    @JsonProperty("GRANTS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGrants(Grants value) { this.grants = value; }

    @JsonProperty("PUBLICATIONS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Publications getPublications() { return publications; }
    @JsonProperty("PUBLICATIONS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPublications(Publications value) { this.publications = value; }

    @JsonProperty("STUDY_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyLinks getStudyLinks() { return studyLinks; }
    @JsonProperty("STUDY_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyLinks(StudyLinks value) { this.studyLinks = value; }

    @JsonProperty("STUDY_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyAttributes getStudyAttributes() { return studyAttributes; }
    @JsonProperty("STUDY_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyAttributes(StudyAttributes value) { this.studyAttributes = value; }
}
