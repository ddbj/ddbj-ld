package com.ddbj.ld.data.beans.sra.analysis;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;

@XmlRootElement(name = "ANALYSIS") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ANALYSISClass implements IPropertiesBean {
    @XmlAttribute(name = "alias")
    @JsonProperty("alias")
    private String alias;

    @XmlAttribute(name = "center_name")
    @JsonProperty("center_name")
    private String centerName;

    @XmlAttribute(name = "broker_name")
    @JsonProperty("broker_name")
    private String brokerName;

    @XmlAttribute(name = "accession")
    @JsonProperty("accession")
    private String accession;

    @XmlAttribute(name = "analysis_center")
    @JsonProperty("analysis_center")
    private String analysisCenter;

    @XmlAttribute(name = "analysis_date")
    @JsonProperty("analysis_date")
    private OffsetDateTime analysisDate;

    @XmlElement(name = "IDENTIFIERS")
    @JsonProperty("IDENTIFIERS")
    private Identifiers identifiers;

    @XmlElement(name = "TITLE")
    @JsonProperty("TITLE")
    private String title;

    @XmlElement(name = "STUDY_REF")
    @JsonProperty("STUDY_REF")
    private StudyRef studyRef;

    @XmlElement(name = "DESCRIPTION")
    @JsonProperty("DESCRIPTION")
    private String description;

    @XmlElement(name = "ANALYSIS_TYPE")
    @JsonProperty("ANALYSIS_TYPE")
    private AnalysisType analysisType;

    @XmlElement(name = "TARGETS")
    @JsonProperty("TARGETS")
    private Targets targets;

    @XmlElement(name = "DATA_BLOCK")
    @JsonProperty("DATA_BLOCK")
    @JsonDeserialize(using = AnalysisDeserializers.DataBlockListDeserializer.class)
    private List<DataBlock> dataBlock;

    @XmlElement(name = "ANALYSIS_LINKS")
    @JsonProperty("ANALYSIS_LINKS")
    private AnalysisLinks analysisLinks;

    @XmlElement(name = "ANALYSIS_ATTRIBUTES")
    @JsonProperty("ANALYSIS_ATTRIBUTES")
    private AnalysisAttributes analysisAttributes;
}
