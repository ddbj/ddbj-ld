package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.sra.common.XrefLink;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Locus {
    @XmlAttribute(name = "locus_name")
    @JsonProperty("locus_name")
    private String locusName;

    @XmlAttribute(name = "description")
    @JsonProperty("description")
    private String description;

    @XmlElement(name = "PROBE_SET")
    @JsonProperty("PROBE_SET")
    private XrefLink probeSet;
}
