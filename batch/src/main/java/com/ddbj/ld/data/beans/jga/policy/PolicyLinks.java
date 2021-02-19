package com.ddbj.ld.data.beans.jga.policy;

import com.fasterxml.jackson.annotation.*;

public class PolicyLinks {
    private PolicyLink policyLink;

    @JsonProperty("POLICY_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PolicyLink getPolicyLink() { return policyLink; }
    @JsonProperty("POLICY_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPolicyLink(PolicyLink value) { this.policyLink = value; }
}
