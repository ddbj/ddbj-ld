package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class PeerProject {
<<<<<<< HEAD
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
=======
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
>>>>>>> 取り込み、修正
