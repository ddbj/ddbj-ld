package com.ddbj.ld.data.beans.sra.study;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "STUDY") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @XmlElement(name = "IDENTIFIERS")
    @JsonProperty("IDENTIFIERS")
    private Identifiers identifiers;

    @XmlElement(name = "DESCRIPTOR")
    @JsonProperty("DESCRIPTOR")
    private Descriptor descriptor;

    @XmlElement(name = "STUDY_LINKS")
    @JsonProperty("STUDY_LINKS")
    private StudyLinks studyLinks;

    @XmlElement(name = "STUDY_ATTRIBUTES")
    @JsonProperty("STUDY_ATTRIBUTES")
    private StudyAttributes studyAttributes;
}
