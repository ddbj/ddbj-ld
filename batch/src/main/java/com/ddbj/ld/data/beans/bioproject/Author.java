package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {
    private Name name;
    private String consortium;

    @JsonProperty("Name")
    public Name getName() { return name; }
    @JsonProperty("Name")
    public void setName(Name value) { this.name = value; }

    @JsonProperty("Consortium")
    public String getConsortium() { return consortium; }
    @JsonProperty("Consortium")
    public void setConsortium(String value) { this.consortium = value; }
}
