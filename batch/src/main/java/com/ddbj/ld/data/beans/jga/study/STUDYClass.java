package com.ddbj.ld.data.beans.jga.study;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "STUDY") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class STUDYClass implements IPropertiesBean {
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

    @XmlElement(name = "DESCRIPTOR")
    @JsonProperty("DESCRIPTOR")
    private Descriptor descriptor;

    @XmlElement(name = "GRANTS")
    @JsonProperty("GRANTS")
    private Grants grants;

    @XmlElement(name = "PUBLICATIONS")
    @JsonProperty("PUBLICATIONS")
    private Publications publications;

    @XmlElement(name = "STUDY_LINKS")
    @JsonProperty("STUDY_LINKS")
    private StudyLinks studyLinks;

    @XmlElement(name = "STUDY_ATTRIBUTES")
    @JsonProperty("STUDY_ATTRIBUTES")
    private StudyAttributes studyAttributes;
}
