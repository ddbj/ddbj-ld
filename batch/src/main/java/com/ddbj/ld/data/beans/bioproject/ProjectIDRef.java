package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectIDRef {
    private String archive;
    private String id;
    private String accession;

    @JsonProperty("archive")
<<<<<<< HEAD
    public String getArchive() { return archive; }
    @JsonProperty("archive")
    public void setArchive(String value) { this.archive = value; }

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("accession")
    public String getAccession() { return accession; }
    @JsonProperty("accession")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getArchive() { return archive; }
    @JsonProperty("archive")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setArchive(String value) { this.archive = value; }

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.id = value; }

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setAccession(String value) { this.accession = value; }
}
