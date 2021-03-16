package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Member {
    private String refname;
    private String refcenter;
    private String accession;
    private String memberName;
    private String proportion;
    private Identifiers identifiers;
    private ReadLabel readLabel;

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

    @JsonProperty("member_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMemberName() { return memberName; }
    @JsonProperty("member_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMemberName(String value) { this.memberName = value; }

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
