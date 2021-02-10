package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class AuthorSet {
    private Author author;

    @JsonProperty("Author")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Author getAuthor() { return author; }
    @JsonProperty("Author")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAuthor(Author value) { this.author = value; }
}