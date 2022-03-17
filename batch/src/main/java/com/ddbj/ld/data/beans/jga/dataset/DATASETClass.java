package com.ddbj.ld.data.beans.jga.dataset;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "DATASET") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DATASETClass implements IPropertiesBean {
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

    @XmlAttribute(name = "data_version")
    @JsonProperty("data_version")
    private String dataVersion;

    @XmlAttribute(name = "participant_set_version")
    @JsonProperty("participant_set_version")
    private String participantSetVersion;

    @XmlAttribute(name = "submission_date")
    @JsonProperty("submission_date")
    private String submissionDate;

    @XmlAttribute(name = "last_update")
    @JsonProperty("last_update")
    private String lastUpdate;

    @XmlElement(name = "IDENTIFIERS")
    @JsonProperty("IDENTIFIERS")
    private Identifiers identifiers;

    @XmlElement(name = "TITLE")
    @JsonProperty("TITLE")
    private String title;

    @XmlElement(name = "DESCRIPTION")
    @JsonProperty("DESCRIPTION")
    private String description;

    @XmlElement(name = "DATASET_TYPE")
    @JsonProperty("DATASET_TYPE")
    private String datasetType;

    @XmlElement(name = "DATA_REFS")
    @JsonProperty("DATA_REFS")
    @JsonDeserialize(using = DataSetDeserializers.DataRefsListDeserializer.class)
    private List<DataRefs> dataRefs;

    @XmlElement(name = "ANALYSIS_REFS")
    @JsonProperty("ANALYSIS_REFS")
    @JsonDeserialize(using = DataSetDeserializers.AnalysisRefsListDeserializer.class)
    private List<AnalysisRefs> analysisRefs;

    @XmlElement(name = "POLICY_REF")
    @JsonProperty("POLICY_REF")
    private Ref policyRef;

    @XmlElement(name = "DATASET_LINKS")
    @JsonProperty("DATASET_LINKS")
    private DatasetLinks datasetLinks;

    @XmlElement(name = "DATASET_ATTRIBUTES")
    @JsonProperty("DATASET_ATTRIBUTES")
    private DatasetAttributes datasetAttributes;
}
