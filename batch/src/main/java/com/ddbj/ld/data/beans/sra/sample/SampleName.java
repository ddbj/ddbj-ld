package com.ddbj.ld.data.beans.sra.sample;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SampleName {
    @XmlElement(name = "TAXON_ID")
    @JsonProperty("TAXON_ID")
    private int taxonID;

    @XmlElement(name = "SCIENTIFIC_NAME")
    @JsonProperty("SCIENTIFIC_NAME")
    private String scientificName;

    @XmlElement(name = "COMMON_NAME")
    @JsonProperty("COMMON_NAME")
    private String commonName;

    @XmlElement(name = "ANONYMIZED_NAME")
    @JsonProperty("ANONYMIZED_NAME")
    private String anonymizedName;

    @XmlElement(name = "INDIVIDUAL_NAME")
    @JsonProperty("INDIVIDUAL_NAME")
    private String individualName;
}
