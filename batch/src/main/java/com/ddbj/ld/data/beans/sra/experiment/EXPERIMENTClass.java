package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.ddbj.ld.data.beans.sra.common.CommonDeserializers;
import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.ddbj.ld.data.beans.sra.common.Platform;
import com.ddbj.ld.data.beans.sra.common.Processing;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "EXPERIMENT") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EXPERIMENTClass implements IPropertiesBean {
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

    @XmlElement(name = "STUDY_REF")
    @JsonProperty("STUDY_REF")
    private StudyRef studyRef;

    @XmlElement(name = "DESIGN")
    @JsonProperty("DESIGN")
    private Design design;

    @XmlElement(name = "PLATFORM")
    @JsonProperty("PLATFORM")
    private Platform platform;

    @XmlElement(name = "PROCESSING")
    @JsonProperty("PROCESSING")
    @JsonDeserialize( using = CommonDeserializers.ProcessingDeserializer.class)
    private Processing processing;

    @XmlElement(name = "EXPERIMENT_LINKS")
    @JsonProperty("EXPERIMENT_LINKS")
    private ExperimentLinks experimentLinks;

    @XmlElement(name = "EXPERIMENT_ATTRIBUTES")
    @JsonProperty("EXPERIMENT_ATTRIBUTES")
    private ExperimentAttributes experimentAttributes;
}
