package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Author {
    private Name name;
    private String consortium;

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Name getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(Name value) { this.name = value; }

    @JsonProperty("Consortium")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getConsortium() { return consortium; }
    @JsonProperty("Consortium")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setConsortium(String value) { this.consortium = value; }
}