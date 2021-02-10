package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ArchiveID {
    private String archive;
    private String id;
    private String accession;

    @JsonProperty("archive")
    public String getArchive() { return archive; }
    @JsonProperty("archive")
    public void setArchive(String value) { this.archive = value; }

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.id = value; }

    @JsonProperty("accession")
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    public void setAccession(String value) { this.accession = value; }
}
