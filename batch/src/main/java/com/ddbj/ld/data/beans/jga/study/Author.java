package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Author {
    private Name name;
    private String consortium;

    @JsonProperty("NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Name getName() { return name; }
    @JsonProperty("NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(Name value) { this.name = value; }

    @JsonProperty("CONSORTIUM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getConsortium() { return consortium; }
    @JsonProperty("CONSORTIUM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setConsortium(String value) { this.consortium = value; }
}
