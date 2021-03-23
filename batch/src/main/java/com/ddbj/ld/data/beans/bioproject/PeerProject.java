package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class PeerProject {
    private String bioticRelationship;
    private String commonInputData;
    private ProjectIDRef memberID;

    @JsonProperty("BioticRelationship")
    public String getBioticRelationship() { return bioticRelationship; }
    @JsonProperty("BioticRelationship")
    public void setBioticRelationship(String value) { this.bioticRelationship = value; }

    @JsonProperty("CommonInputData")
    public String getCommonInputData() { return commonInputData; }
    @JsonProperty("CommonInputData")
    public void setCommonInputData(String value) { this.commonInputData = value; }

    @JsonProperty("MemberID")
    public ProjectIDRef getMemberID() { return memberID; }
    @JsonProperty("MemberID")
    public void setMemberID(ProjectIDRef value) { this.memberID = value; }
}
