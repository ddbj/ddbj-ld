package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Identifiers {
    @XmlElement(name="PRIMARY_ID")
    @JsonProperty("PRIMARY_ID")
    private PrimaryID primaryID;

    @XmlElement(name="SECONDARY_ID")
    @JsonProperty("SECONDARY_ID")
    private PrimaryID secondaryID;

    @XmlElement(name="EXTERNAL_ID")
    @JsonProperty("EXTERNAL_ID")
    private ID externalID;

    @XmlElement(name="SUBMITTER_ID")
    @JsonProperty("SUBMITTER_ID")
    private ID submitterID;

    @XmlElement(name="UUID")
    @JsonProperty("UUID")
    private PrimaryID uuid;
}
