package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class EXPERIMENTClass {
    private Title alias;
    private Title centerName;
    private Title brokerName;
    private Title accession;
    private Identifiers identifiers;
    private Title title;
    private StudyRef studyRef;
    private Design design;
    private Platform platform;
    private Processing processing;
    private ExperimentLinks experimentLinks;
    private ExperimentAttributes experimentAttributes;

    @JsonProperty("alias")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getAlias() { return alias; }
    @JsonProperty("alias")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAlias(Title value) { this.alias = value; }

    @JsonProperty("center_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getCenterName() { return centerName; }
    @JsonProperty("center_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCenterName(Title value) { this.centerName = value; }

    @JsonProperty("broker_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getBrokerName() { return brokerName; }
    @JsonProperty("broker_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBrokerName(Title value) { this.brokerName = value; }

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccession(Title value) { this.accession = value; }

    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Identifiers getIdentifiers() { return identifiers; }
    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIdentifiers(Identifiers value) { this.identifiers = value; }

    @JsonProperty("TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getTitle() { return title; }
    @JsonProperty("TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTitle(Title value) { this.title = value; }

    @JsonProperty("STUDY_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyRef getStudyRef() { return studyRef; }
    @JsonProperty("STUDY_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyRef(StudyRef value) { this.studyRef = value; }

    @JsonProperty("DESIGN")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Design getDesign() { return design; }
    @JsonProperty("DESIGN")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDesign(Design value) { this.design = value; }

    @JsonProperty("PLATFORM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Platform getPlatform() { return platform; }
    @JsonProperty("PLATFORM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPlatform(Platform value) { this.platform = value; }

    @JsonProperty("PROCESSING")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Processing getProcessing() { return processing; }
    @JsonProperty("PROCESSING")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProcessing(Processing value) { this.processing = value; }

    @JsonProperty("EXPERIMENT_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ExperimentLinks getExperimentLinks() { return experimentLinks; }
    @JsonProperty("EXPERIMENT_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExperimentLinks(ExperimentLinks value) { this.experimentLinks = value; }

    @JsonProperty("EXPERIMENT_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ExperimentAttributes getExperimentAttributes() { return experimentAttributes; }
    @JsonProperty("EXPERIMENT_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExperimentAttributes(ExperimentAttributes value) { this.experimentAttributes = value; }
}
