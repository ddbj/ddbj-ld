package com.ddbj.ld.data.beans.jga.policy;

import com.fasterxml.jackson.annotation.*;

public class Link {
    private String db;
    private String id;
    private String query;
    private String label;

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

    @JsonProperty("QUERY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getQuery() { return query; }
    @JsonProperty("QUERY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setQuery(String value) { this.query = value; }

    @JsonProperty("LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabel() { return label; }
    @JsonProperty("LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(String value) { this.label = value; }
}
