package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class XrefLink {
    private String db;
    private String id;
    private String label;

    @JsonProperty("DB")
    public String getDB() { return db; }
    @JsonProperty("DB")
    public void setDB(String value) { this.db = value; }

    @JsonProperty("ID")
    public String getID() { return id; }
    @JsonProperty("ID")
    public void setID(String value) { this.id = value; }

    @JsonProperty("LABEL")
    public String getLabel() { return label; }
    @JsonProperty("LABEL")
    public void setLabel(String value) { this.label = value; }
}
