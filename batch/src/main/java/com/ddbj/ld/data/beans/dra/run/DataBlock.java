package com.ddbj.ld.data.beans.dra.run;

import com.fasterxml.jackson.annotation.*;

public class DataBlock {
    private String memberName;
    private Files files;

    @JsonProperty("member_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMemberName() { return memberName; }
    @JsonProperty("member_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMemberName(String value) { this.memberName = value; }

    @JsonProperty("FILES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Files getFiles() { return files; }
    @JsonProperty("FILES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFiles(Files value) { this.files = value; }
}
