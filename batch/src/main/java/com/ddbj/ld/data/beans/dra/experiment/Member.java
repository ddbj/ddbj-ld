package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Member {
    private Title refname;
    private Title refcenter;
    private Title accession;
    private Title memberName;
    private String proportion;
    private Identifiers identifiers;
    private ReadLabel readLabel;

    @JsonProperty("refname")
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonProperty("member_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getMemberName() { return memberName; }
    @JsonProperty("member_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMemberName(Title value) { this.memberName = value; }

    @JsonProperty("proportion")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProportion() { return proportion; }
    @JsonProperty("proportion")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProportion(String value) { this.proportion = value; }

    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Identifiers getIdentifiers() { return identifiers; }
    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIdentifiers(Identifiers value) { this.identifiers = value; }

    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ReadLabel getReadLabel() { return readLabel; }
    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadLabel(ReadLabel value) { this.readLabel = value; }
}
