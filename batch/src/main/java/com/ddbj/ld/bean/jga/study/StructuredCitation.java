package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class StructuredCitation {
    private String title;
    private Journal journal;
    private AuthorSet authorSet;

    @JsonProperty("TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTitle() { return title; }
    @JsonProperty("TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("JOURNAL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Journal getJournal() { return journal; }
    @JsonProperty("JOURNAL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setJournal(Journal value) { this.journal = value; }

    @JsonProperty("AUTHOR_SET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AuthorSet getAuthorSet() { return authorSet; }
    @JsonProperty("AUTHOR_SET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAuthorSet(AuthorSet value) { this.authorSet = value; }
}
