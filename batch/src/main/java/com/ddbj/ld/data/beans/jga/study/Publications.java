package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

public class Publications {
    private List<Publication> publication;

    @JsonProperty("PUBLICATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Publication> getPublication() { return publication; }
    @JsonProperty("PUBLICATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPublication(List<Publication> value) { this.publication = value; }
}
