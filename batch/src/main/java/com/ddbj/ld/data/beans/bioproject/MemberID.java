package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class MemberID {
    private String archive;
    private String accession;
    private long id;

    @JsonProperty("archive")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getArchive() { return archive; }
    @JsonProperty("archive")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setArchive(String value) { this.archive = value; }

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccession(String value) { this.accession = value; }

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public long getID() { return id; }
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(long value) { this.id = value; }
}