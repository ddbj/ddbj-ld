package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;

public class StudyRef {
    private String refname;
    private String refcenter;
    private String accession;
    private Identifiers identifiers;
    private Pool pool;

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

    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Identifiers getIdentifiers() { return identifiers; }
    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIdentifiers(Identifiers value) { this.identifiers = value; }

    @JsonProperty("POOL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Pool getPool() { return pool; }
    @JsonProperty("POOL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPool(Pool value) { this.pool = value; }
}
