package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Link {
    private String db;
    private String id;
    private String label;
    private String query;

    @JsonProperty("DB")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDB() { return db; }
    @JsonProperty("DB")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDB(String value) { this.db = value; }

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.id = value; }

    @JsonProperty("LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabel() { return label; }
    @JsonProperty("LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("QUERY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getQuery() { return query; }
    @JsonProperty("QUERY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setQuery(String value) { this.query = value; }
}
