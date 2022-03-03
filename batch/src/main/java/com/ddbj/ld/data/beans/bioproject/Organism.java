package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Organism {
    @XmlAttribute(name = "taxID")
    @JsonProperty("taxID")
    private String taxID;

    @XmlAttribute(name = "species")
    @JsonProperty("species")
    private String species;

    @XmlElement(name = "OrganismName")
    @JsonProperty("OrganismName")
    private String organismName;

    @XmlElement(name = "Label")
    @JsonProperty("Label")
    private String label;

    @XmlElement(name = "Strain")
    @JsonProperty("Strain")
    private String strain;

    @XmlElement(name = "IsolateName")
    @JsonProperty("IsolateName")
    private String isolateName;

    @XmlElement(name = "Breed")
    @JsonProperty("Breed")
    private String breed;

    @XmlElement(name = "Cultivar")
    @JsonProperty("Cultivar")
    private String cultivar;

    @XmlElement(name = "Supergroup")
    @JsonProperty("Supergroup")
    private String supergroup;

    @XmlElement(name = "BiologicalProperties")
    @JsonProperty("BiologicalProperties")
    private BiologicalProperties biologicalProperties;

    @XmlElement(name = "Organization")
    @JsonProperty("Organization")
    private String organization;

    @XmlElement(name = "Reproduction")
    @JsonProperty("Reproduction")
    private String reproduction;

    @XmlElement(name = "RepliconSet")
    @JsonProperty("RepliconSet")
    private RepliconSet repliconSet;

    @XmlElement(name = "GenomeSize")
    @JsonProperty("GenomeSize")
    private Size genomeSize;
}
