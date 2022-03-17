package com.ddbj.ld.data.beans.jga.dataset;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Identifiers {
    @XmlElement(name = "PRIMARY_ID")
    @JsonProperty("PRIMARY_ID")
    private String primaryID;

    @XmlElement(name = "SECONDARY_ID")
    @JsonProperty("SECONDARY_ID")
    private String secondaryID;

    @XmlElement(name = "EXTERNAL_ID")
    @JsonProperty("EXTERNAL_ID")
    private ID externalID;

    @XmlElement(name = "SUBMITTER_ID")
    @JsonProperty("SUBMITTER_ID")
    private ID submitterID;

    @XmlElement(name = "UUID")
    @JsonProperty("UUID")
    private String uuid;
}
