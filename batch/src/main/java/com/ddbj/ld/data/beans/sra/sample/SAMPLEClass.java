package com.ddbj.ld.data.beans.sra.sample;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "SAMPLE") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SAMPLEClass implements IPropertiesBean {
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

    @XmlElement(name = "TITLE")
    @JsonProperty("TITLE")
    private String title;

    @XmlElement(name = "SAMPLE_NAME")
    @JsonProperty("SAMPLE_NAME")
    private SampleName sampleName;

    @XmlElement(name = "DESCRIPTION")
    @JsonProperty("DESCRIPTION")
    private String description;

    @XmlElement(name = "SAMPLE_LINKS")
    @JsonProperty("SAMPLE_LINKS")
    private SampleLinks sampleLinks;

    @XmlElement(name = "SAMPLE_ATTRIBUTES")
    @JsonProperty("SAMPLE_ATTRIBUTES")
    private SampleAttributes sampleAttributes;
}
