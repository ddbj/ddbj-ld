package com.ddbj.ld.data.beans.sra.run;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.ddbj.ld.data.beans.sra.common.Platform;
import com.ddbj.ld.data.beans.sra.common.Processing;
import com.ddbj.ld.data.beans.sra.common.SpotDescriptor;
import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RUNClass implements IPropertiesBean {
    private String alias;
    private String centerName;
    private String brokerName;
    private String accession;
    private OffsetDateTime runDate;
    private String runCenter;
    private Identifiers identifiers;
    private String title;
    private ExperimentRef experimentRef;
    private SpotDescriptor spotDescriptor;
    private Platform platform;
    private Processing processing;
    private DataBlock dataBlock;
    private RunLinks runLinks;
    private RunAttributes runAttributes;

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

    @JsonProperty("run_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getRunDate() { return runDate; }
    @JsonProperty("run_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRunDate(OffsetDateTime value) { this.runDate = value; }

    @JsonProperty("run_center")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRunCenter() { return runCenter; }
    @JsonProperty("run_center")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRunCenter(String value) { this.runCenter = value; }

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

    @JsonProperty("EXPERIMENT_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ExperimentRef getExperimentRef() { return experimentRef; }
    @JsonProperty("EXPERIMENT_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExperimentRef(ExperimentRef value) { this.experimentRef = value; }

    @JsonProperty("SPOT_DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SpotDescriptor getSpotDescriptor() { return spotDescriptor; }
    @JsonProperty("SPOT_DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSpotDescriptor(SpotDescriptor value) { this.spotDescriptor = value; }

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

    @JsonProperty("DATA_BLOCK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public DataBlock getDataBlock() { return dataBlock; }
    @JsonProperty("DATA_BLOCK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataBlock(DataBlock value) { this.dataBlock = value; }

    @JsonProperty("RUN_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RunLinks getRunLinks() { return runLinks; }
    @JsonProperty("RUN_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRunLinks(RunLinks value) { this.runLinks = value; }

    @JsonProperty("RUN_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RunAttributes getRunAttributes() { return runAttributes; }
    @JsonProperty("RUN_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRunAttributes(RunAttributes value) { this.runAttributes = value; }
}
