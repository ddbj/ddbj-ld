package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Platform {
    @XmlElement(name = "LS454")
    @JsonProperty("LS454")
    private ABISolid ls454;

    @XmlElement(name = "ILLUMINA")
    @JsonProperty("ILLUMINA")
    private ABISolid illumina;

    @XmlElement(name = "HELICOS")
    @JsonProperty("HELICOS")
    private ABISolid helicos;

    @XmlElement(name = "ABI_SOLID")
    @JsonProperty("ABI_SOLID")
    private ABISolid abiSolid;

    @XmlElement(name = "COMPLETE_GENOMICS")
    @JsonProperty("COMPLETE_GENOMICS")
    private ABISolid completeGenomics;

    @XmlElement(name = "BGISEQ")
    @JsonProperty("BGISEQ")
    private ABISolid bgiSeq;

    @XmlElement(name = "OXFORD_NANOPORE")
    @JsonProperty("OXFORD_NANOPORE")
    private ABISolid oxfordNanopore;

    @XmlElement(name = "PACBIO_SMRT")
    @JsonProperty("PACBIO_SMRT")
    private ABISolid pacbioSmrt;

    @XmlElement(name = "ION_TORRENT")
    @JsonProperty("ION_TORRENT")
    private ABISolid ionTorrent;

    @XmlElement(name = "CAPILLARY")
    @JsonProperty("CAPILLARY")
    private ABISolid capillary;
}
