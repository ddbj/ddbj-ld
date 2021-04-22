package com.ddbj.ld.data.beans.jga.policy;

import com.fasterxml.jackson.annotation.*;

public class PolicyAttributes {
    private PolicyAttribute policyAttribute;

    @JsonProperty("POLICY_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PolicyAttribute getPolicyAttribute() { return policyAttribute; }
    @JsonProperty("POLICY_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPolicyAttribute(PolicyAttribute value) { this.policyAttribute = value; }
}
