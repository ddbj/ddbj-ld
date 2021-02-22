package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class PeerProject {
    private MemberID memberID;
    private String commonInputData;
    private String bioticRelationship;

    @JsonProperty("MemberID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public MemberID getMemberID() { return memberID; }
    @JsonProperty("MemberID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMemberID(MemberID value) { this.memberID = value; }

    @JsonProperty("CommonInputData")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCommonInputData() { return commonInputData; }
    @JsonProperty("CommonInputData")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCommonInputData(String value) { this.commonInputData = value; }

    @JsonProperty("BioticRelationship")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBioticRelationship() { return bioticRelationship; }
    @JsonProperty("BioticRelationship")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBioticRelationship(String value) { this.bioticRelationship = value; }

}