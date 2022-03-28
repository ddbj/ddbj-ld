package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true) // OrganismName と同一と思われるtaxonomy_nameをスキップ
@Data
public class BioSampleOrganism {
    @XmlAttribute(name = "taxonomy_id")
    @JsonProperty("taxonomy_id")
    private int taxonomyID;

    @XmlAttribute(name = "role")
    @JsonProperty("role")
    private String role;

    @XmlElement(name = "OrganismName")
    @JsonProperty("OrganismName")
    private String organismName;

    @XmlElement(name = "Label")
    @JsonProperty("Label")
    private String label;

    @XmlElement(name = "IsolateName")
    @JsonProperty("IsolateName")
    private String isolateName;

    @XmlElement(name = "Breed")
    @JsonProperty("Breed")
    private String breed;

    @XmlElement(name = "Strain")
    @JsonProperty("Strain")
    private String strain;

    @XmlElement(name = "Cultivar")
    @JsonProperty("Cultivar")
    private String cultivar;
}
