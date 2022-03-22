package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Identifiers {
    @XmlElement(name = "PRIMARY_ID")
    @JsonProperty("PRIMARY_ID")
    private PrimaryID primaryID;

    @XmlElement(name = "SECONDARY_ID")
    @JsonProperty("SECONDARY_ID")
    @JsonDeserialize(using = CommonDeserializers.PrimaryIDListDeserializer.class)
    private List<PrimaryID> secondaryID;

    @XmlElement(name = "EXTERNAL_ID")
    @JsonProperty("EXTERNAL_ID")
    @JsonDeserialize(using = CommonDeserializers.IDListDeserializer.class)
    private List<ID> externalID;

    @XmlElement(name = "SUBMITTER_ID")
    @JsonProperty("SUBMITTER_ID")
    @JsonDeserialize(using = CommonDeserializers.IDListDeserializer.class)
    private List<ID> submitterID;

    @XmlElement(name = "UUID")
    @JsonProperty("UUID")
    @JsonDeserialize(using = CommonDeserializers.PrimaryIDListDeserializer.class)
    private List<PrimaryID> uuid;
}
