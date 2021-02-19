package com.ddbj.ld.data.beans.jga.policy;

import com.fasterxml.jackson.annotation.*;

public class Policy {
    private POLICYClass policy;

    @JsonProperty("POLICY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public POLICYClass getPolicy() { return policy; }
    @JsonProperty("POLICY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPolicy(POLICYClass value) { this.policy = value; }
}
