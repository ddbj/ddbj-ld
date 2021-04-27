package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Relations {
    private Relation relation;

    @JsonProperty("Relation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Relation getRelation() { return relation; }
    @JsonProperty("Relation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelation(Relation value) { this.relation = value; }
}
