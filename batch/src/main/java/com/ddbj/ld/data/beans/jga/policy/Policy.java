package com.ddbj.ld.data.beans.jga.policy;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;

public class Policy implements IPropertiesBean {
    private POLICYClass policy;

    @JsonProperty("POLICY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public POLICYClass getPolicy() { return policy; }
    @JsonProperty("POLICY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPolicy(POLICYClass value) { this.policy = value; }
}
