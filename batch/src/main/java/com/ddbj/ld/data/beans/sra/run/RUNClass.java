package com.ddbj.ld.data.beans.sra.run;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.ddbj.ld.data.beans.sra.common.Platform;
import com.ddbj.ld.data.beans.sra.common.Processing;
import com.ddbj.ld.data.beans.sra.common.SpotDescriptor;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.time.OffsetDateTime;

@XmlRootElement(name = "RUN") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RUNClass implements IPropertiesBean {
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

    @XmlAttribute(name = "run_date")
    @JsonProperty("run_date")
    private OffsetDateTime runDate;

    @XmlAttribute(name = "run_center")
    @JsonProperty("run_center")
    private String runCenter;

    @XmlElement(name = "IDENTIFIERS")
    @JsonProperty("IDENTIFIERS")
    private Identifiers identifiers;

    @XmlElement(name = "TITLE")
    @JsonProperty("TITLE")
    private String title;

    @XmlElement(name = "EXPERIMENT_REF")
    @JsonProperty("EXPERIMENT_REF")
    private ExperimentRef experimentRef;

    @XmlElement(name = "SPOT_DESCRIPTOR")
    @JsonProperty("SPOT_DESCRIPTOR")
    private SpotDescriptor spotDescriptor;

    @XmlElement(name = "PLATFORM")
    @JsonProperty("PLATFORM")
    private Platform platform;

    @XmlElement(name = "PROCESSING")
    @JsonProperty("PROCESSING")
    private Processing processing;

    @XmlElement(name = "DATA_BLOCK")
    @JsonProperty("DATA_BLOCK")
    private DataBlock dataBlock;

    @XmlElement(name = "RUN_LINKS")
    @JsonProperty("RUN_LINKS")
    private RunLinks runLinks;

    @XmlElement(name = "RUN_ATTRIBUTES")
    @JsonProperty("RUN_ATTRIBUTES")
    private RunAttributes runAttributes;
}
