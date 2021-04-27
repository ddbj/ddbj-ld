package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attributes {
    private Attribute attribute;

    @JsonProperty("Attribute")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Attribute getAttribute() { return attribute; }
    @JsonProperty("Attribute")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAttribute(Attribute value) { this.attribute = value; }
}
