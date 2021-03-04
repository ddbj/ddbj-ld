package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AuthorSet {
    private List<Author> author;

    @JsonProperty("Author")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Author> getAuthor() { return author; }
    @JsonProperty("Author")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAuthor(List<Author> value) { this.author = value; }
}