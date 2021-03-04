package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class StructuredCitation {
    private String title;
    private Journal journal;
    private AuthorSet authorSet;

    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTitle() { return title; }
    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Journal")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Journal getJournal() { return journal; }
    @JsonProperty("Journal")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setJournal(Journal value) { this.journal = value; }

    @JsonProperty("AuthorSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AuthorSet getAuthorSet() { return authorSet; }
    @JsonProperty("AuthorSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAuthorSet(AuthorSet value) { this.authorSet = value; }
}