package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Platform {
    private ABISolid ls454;
    private ABISolid illumina;
    private ABISolid helicos;
    private ABISolid abiSolid;
    private ABISolid completeGenomics;
    private ABISolid oxfordNanopore;
    private ABISolid pacbioSmrt;
    private ABISolid ionTorrent;
    private ABISolid capillary;

    @JsonProperty("LS454")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ABISolid getLs454() { return ls454; }
    @JsonProperty("LS454")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLs454(ABISolid value) { this.ls454 = value; }

    @JsonProperty("ILLUMINA")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ABISolid getIllumina() { return illumina; }
    @JsonProperty("ILLUMINA")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIllumina(ABISolid value) { this.illumina = value; }

    @JsonProperty("HELICOS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ABISolid getHelicos() { return helicos; }
    @JsonProperty("HELICOS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setHelicos(ABISolid value) { this.helicos = value; }

    @JsonProperty("ABI_SOLID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ABISolid getABISolid() { return abiSolid; }
    @JsonProperty("ABI_SOLID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setABISolid(ABISolid value) { this.abiSolid = value; }

    @JsonProperty("COMPLETE_GENOMICS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ABISolid getCompleteGenomics() { return completeGenomics; }
    @JsonProperty("COMPLETE_GENOMICS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCompleteGenomics(ABISolid value) { this.completeGenomics = value; }

    @JsonProperty("OXFORD_NANOPORE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ABISolid getOxfordNanopore() { return oxfordNanopore; }
    @JsonProperty("OXFORD_NANOPORE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOxfordNanopore(ABISolid value) { this.oxfordNanopore = value; }

    @JsonProperty("PACBIO_SMRT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ABISolid getPacbioSmrt() { return pacbioSmrt; }
    @JsonProperty("PACBIO_SMRT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPacbioSmrt(ABISolid value) { this.pacbioSmrt = value; }

    @JsonProperty("ION_TORRENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ABISolid getIonTorrent() { return ionTorrent; }
    @JsonProperty("ION_TORRENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIonTorrent(ABISolid value) { this.ionTorrent = value; }

    @JsonProperty("CAPILLARY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ABISolid getCapillary() { return capillary; }
    @JsonProperty("CAPILLARY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCapillary(ABISolid value) { this.capillary = value; }
}
