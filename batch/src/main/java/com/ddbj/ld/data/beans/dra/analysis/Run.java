package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class Run {
    private String refname;
    private String refcenter;
    private String accession;
    private String dataBlockName;
    private String readGroupLabel;

    @JsonProperty("refname")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRefname() { return refname; }
    @JsonProperty("refname")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRefname(String value) { this.refname = value; }

    @JsonProperty("refcenter")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRefcenter() { return refcenter; }
    @JsonProperty("refcenter")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRefcenter(String value) { this.refcenter = value; }

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccession(String value) { this.accession = value; }

    @JsonProperty("data_block_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDataBlockName() { return dataBlockName; }
    @JsonProperty("data_block_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataBlockName(String value) { this.dataBlockName = value; }

    @JsonProperty("read_group_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadGroupLabel() { return readGroupLabel; }
    @JsonProperty("read_group_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadGroupLabel(String value) { this.readGroupLabel = value; }
}
