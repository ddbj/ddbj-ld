package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;

public class AuthorSet {
    private Author author;

    @JsonProperty("AUTHOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Author getAuthor() { return author; }
    @JsonProperty("AUTHOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAuthor(Author value) { this.author = value; }
}
