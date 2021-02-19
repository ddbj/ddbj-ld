package com.ddbj.ld.bean.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class Sequence {
    private String accession;
    private String gi;
    private String dataBlockName;
    private String seqLabel;

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccession(String value) { this.accession = value; }

    @JsonProperty("gi")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGi() { return gi; }
    @JsonProperty("gi")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGi(String value) { this.gi = value; }

    @JsonProperty("data_block_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDataBlockName() { return dataBlockName; }
    @JsonProperty("data_block_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataBlockName(String value) { this.dataBlockName = value; }

    @JsonProperty("seq_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSeqLabel() { return seqLabel; }
    @JsonProperty("seq_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSeqLabel(String value) { this.seqLabel = value; }
}
