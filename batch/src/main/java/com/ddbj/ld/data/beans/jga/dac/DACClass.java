package com.ddbj.ld.data.beans.jga.dac;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "DAC") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DACClass implements IPropertiesBean {
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

    @XmlElement(name = "CONTACTS")
    @JsonProperty("CONTACTS")
    private Contacts contacts;

    @XmlElement(name = "DAC_LINKS")
    @JsonProperty("DAC_LINKS")
    private DACLinks dacLinks;

    @XmlElement(name = "DAC_ATTRIBUTES")
    @JsonProperty("DAC_ATTRIBUTES")
    private DACAttributes dacAttributes;
}
