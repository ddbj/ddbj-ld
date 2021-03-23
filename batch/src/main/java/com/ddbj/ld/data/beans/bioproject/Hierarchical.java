package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Hierarchical {
    private String type;
    private ProjectIDRef memberID;

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("MemberID")
    public ProjectIDRef getMemberID() { return memberID; }
    @JsonProperty("MemberID")
    public void setMemberID(ProjectIDRef value) { this.memberID = value; }
}
