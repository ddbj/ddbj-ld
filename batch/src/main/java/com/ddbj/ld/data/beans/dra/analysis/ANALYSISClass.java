package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class ANALYSISClass {
    private String alias;
    private String centerName;
    private String brokerName;
    private String accession;
    private String analysisCenter;
    private OffsetDateTime analysisDate;
    private Identifiers identifiers;
    private String title;
    private StudyRef studyRef;
    private String description;
    private AnalysisType analysisType;
    private Targets targets;
    private DataBlock dataBlock;
    private AnalysisLinks analysisLinks;
    private AnalysisAttributes analysisAttributes;

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

    @JsonProperty("analysis_center")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAnalysisCenter() { return analysisCenter; }
    @JsonProperty("analysis_center")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisCenter(String value) { this.analysisCenter = value; }

    @JsonProperty("analysis_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getAnalysisDate() { return analysisDate; }
    @JsonProperty("analysis_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisDate(OffsetDateTime value) { this.analysisDate = value; }

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

    @JsonProperty("STUDY_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyRef getStudyRef() { return studyRef; }
    @JsonProperty("STUDY_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyRef(StudyRef value) { this.studyRef = value; }

    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("ANALYSIS_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AnalysisType getAnalysisType() { return analysisType; }
    @JsonProperty("ANALYSIS_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisType(AnalysisType value) { this.analysisType = value; }

    @JsonProperty("TARGETS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Targets getTargets() { return targets; }
    @JsonProperty("TARGETS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTargets(Targets value) { this.targets = value; }

    @JsonProperty("DATA_BLOCK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DataBlock getDataBlock() { return dataBlock; }
    @JsonProperty("DATA_BLOCK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataBlock(DataBlock value) { this.dataBlock = value; }

    @JsonProperty("ANALYSIS_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AnalysisLinks getAnalysisLinks() { return analysisLinks; }
    @JsonProperty("ANALYSIS_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisLinks(AnalysisLinks value) { this.analysisLinks = value; }

    @JsonProperty("ANALYSIS_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AnalysisAttributes getAnalysisAttributes() { return analysisAttributes; }
    @JsonProperty("ANALYSIS_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisAttributes(AnalysisAttributes value) { this.analysisAttributes = value; }
}
