package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Hierarchical {
    private String type;
    private ProjectIDRef memberID;

    @JsonProperty("type")
<<<<<<< HEAD
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("MemberID")
    public ProjectIDRef getMemberID() { return memberID; }
    @JsonProperty("MemberID")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getType() { return type; }
    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setType(String value) { this.type = value; }

    @JsonProperty("MemberID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectIDRef getMemberID() { return memberID; }
    @JsonProperty("MemberID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setMemberID(ProjectIDRef value) { this.memberID = value; }
}
