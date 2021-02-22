package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Grants {
    private List<Grant> grant;

    @JsonProperty("GRANT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Grant> getGrant() { return grant; }
    @JsonProperty("GRANT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGrant(List<Grant> value) { this.grant = value; }
}
