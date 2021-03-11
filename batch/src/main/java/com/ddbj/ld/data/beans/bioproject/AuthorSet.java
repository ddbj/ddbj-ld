package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class AuthorSet {
    private Author author;

    @JsonProperty("Author")
    public Author getAuthor() { return author; }
    @JsonProperty("Author")
    public void setAuthor(Author value) { this.author = value; }
}
