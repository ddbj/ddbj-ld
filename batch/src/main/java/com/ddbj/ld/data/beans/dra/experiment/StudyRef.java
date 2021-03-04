package com.ddbj.ld.data.beans.dra.experiment;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;

public class StudyRef {
    private String refname;
    private String refcenter;
    private String accession;
=======
import com.fasterxml.jackson.annotation.*;

public class StudyRef {
    private Title refname;
    private Title refcenter;
    private Title accession;
>>>>>>> 取り込み、修正
    private Identifiers identifiers;
    private Pool pool;

    @JsonProperty("refname")
    @JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<< HEAD
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
=======
    public Title getRefname() { return refname; }
    @JsonProperty("refname")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRefname(Title value) { this.refname = value; }

    @JsonProperty("refcenter")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getRefcenter() { return refcenter; }
    @JsonProperty("refcenter")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRefcenter(Title value) { this.refcenter = value; }

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccession(Title value) { this.accession = value; }
>>>>>>> 取り込み、修正

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
