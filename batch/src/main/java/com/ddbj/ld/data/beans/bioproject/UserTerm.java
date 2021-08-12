package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTerm {
    private String term;
    private String category;
    private String units;
    private String content;

    @JsonProperty("term")
    public String getTerm() { return term; }
    @JsonProperty("term")
    public void setTerm(String value) { this.term = value; }

    @JsonProperty("category")
    public String getCategory() { return category; }
    @JsonProperty("category")
    public void setCategory(String value) { this.category = value; }

    @JsonProperty("units")
    public String getUnits() { return units; }
    @JsonProperty("units")
    public void setUnits(String value) { this.units = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
