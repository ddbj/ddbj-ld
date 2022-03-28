package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // centerを無視
@Data
public class ProjectID {
    @XmlElement(name = "ArchiveID")
    @JsonProperty("ArchiveID")
    @JsonDeserialize(using = BioProjectDeserializers.ArchiveIDListDeserializer.class)
    private List<ArchiveID> archiveID;

    @XmlElement(name = "SecondaryArchiveID")
    @JsonProperty("SecondaryArchiveID")
    @JsonDeserialize(using = BioProjectDeserializers.ArchiveIDListDeserializer.class)
    private List<ArchiveID> secondaryArchiveID;

    @XmlElement(name = "CenterID")
    @JsonProperty("CenterID")
    @JsonDeserialize(using = BioProjectDeserializers.CenterIDListDeserializer.class)
    private List<CenterID> centerID;

    @XmlElement(name = "LocalID")
    @JsonProperty("LocalID")
    @JsonDeserialize(using = BioProjectDeserializers.LocalIDListDeserializer.class)
    private List<LocalID> localID;
}
