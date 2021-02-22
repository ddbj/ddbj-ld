package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;

public class Comment {
    private String paragraph;
    private Table table;

    @JsonProperty("Paragraph")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getParagraph() { return paragraph; }
    @JsonProperty("Paragraph")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setParagraph(String value) { this.paragraph = value; }

    @JsonProperty("Table")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Table getTable() { return table; }
    @JsonProperty("Table")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTable(Table value) { this.table = value; }
}
