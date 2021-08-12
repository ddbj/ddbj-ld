package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StructuredCitation {
    private String title;
    private Journal journal;
    private AuthorSet authorSet;

    @JsonProperty("Title")
    public String getTitle() { return title; }
    @JsonProperty("Title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Journal")
    public Journal getJournal() { return journal; }
    @JsonProperty("Journal")
    public void setJournal(Journal value) { this.journal = value; }

    @JsonProperty("AuthorSet")
    public AuthorSet getAuthorSet() { return authorSet; }
    @JsonProperty("AuthorSet")
    public void setAuthorSet(AuthorSet value) { this.authorSet = value; }
}
