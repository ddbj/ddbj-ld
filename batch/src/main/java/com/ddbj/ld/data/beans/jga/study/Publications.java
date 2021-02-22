package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Publications {
    private Publication publication;

    @JsonProperty("PUBLICATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Publication getPublication() { return publication; }
    @JsonProperty("PUBLICATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPublication(Publication value) { this.publication = value; }
}
