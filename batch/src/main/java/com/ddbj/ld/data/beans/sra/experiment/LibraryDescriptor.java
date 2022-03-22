package com.ddbj.ld.data.beans.sra.experiment;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LibraryDescriptor {
    @XmlElement(name = "LIBRARY_NAME")
    @JsonProperty("LIBRARY_NAME")
    private String libraryName;

    @XmlElement(name = "LIBRARY_STRATEGY")
    @JsonProperty("LIBRARY_STRATEGY")
    private String libraryStrategy;

    @XmlElement(name = "LIBRARY_SOURCE")
    @JsonProperty("LIBRARY_SOURCE")
    private String librarySource;

    @XmlElement(name = "LIBRARY_SELECTION")
    @JsonProperty("LIBRARY_SELECTION")
    private String librarySelection;

    @XmlElement(name = "LIBRARY_LAYOUT")
    @JsonProperty("LIBRARY_LAYOUT")
    private LibraryLayout libraryLayout;

    @XmlElement(name = "TARGETED_LOCI")
    @JsonProperty("TARGETED_LOCI")
    private TargetedLoci targetedLoci;

    @XmlElement(name = "LIBRARY_CONSTRUCTION_PROTOCOL")
    @JsonProperty("LIBRARY_CONSTRUCTION_PROTOCOL")
    private String libraryConstructionProtocol;
}
