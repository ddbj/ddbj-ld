package com.ddbj.ld.data.beans.jga.policy;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "POLICY") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class POLICYClass implements IPropertiesBean {
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

    @XmlElement(name = "DAC_REF")
    @JsonProperty("DAC_REF")
    private DACRef dacRef;

    @XmlElement(name = "POLICY_TEXT")
    @JsonProperty("POLICY_TEXT")
    private String policyText;

    @XmlElement(name = "POLICY_FILE")
    @JsonProperty("POLICY_FILE")
    private String policyFile;

    @XmlElement(name = "POLICY_LINKS")
    @JsonProperty("POLICY_LINKS")
    private PolicyLinks policyLinks;

    @XmlElement(name = "POLICY_ATTRIBUTES")
    @JsonProperty("POLICY_ATTRIBUTES")
    private PolicyAttributes policyAttributes;
}
