package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Grants {
    private Grant grant;

    @JsonProperty("GRANT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Grant getGrant() { return grant; }
    @JsonProperty("GRANT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGrant(Grant value) { this.grant = value; }
}
