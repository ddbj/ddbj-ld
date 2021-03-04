package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Pool {
    private Member defaultMember;
    private Member member;

    @JsonProperty("DEFAULT_MEMBER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Member getDefaultMember() { return defaultMember; }
    @JsonProperty("DEFAULT_MEMBER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDefaultMember(Member value) { this.defaultMember = value; }

    @JsonProperty("MEMBER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Member getMember() { return member; }
    @JsonProperty("MEMBER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMember(Member value) { this.member = value; }
}
