package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ExternalLink {
    private String label;
    private String category;
    private String url;
    private DBXREF dbXREF;

    @JsonProperty("label")
    public String getLabel() { return label; }
    @JsonProperty("label")
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("category")
    public String getCategory() { return category; }
    @JsonProperty("category")
    public void setCategory(String value) { this.category = value; }

    @JsonProperty("URL")
    public String getURL() { return url; }
    @JsonProperty("URL")
    public void setURL(String value) { this.url = value; }

    @JsonProperty("dbXREF")
    public DBXREF getDBXREF() { return dbXREF; }
    @JsonProperty("dbXREF")
    public void setDBXREF(DBXREF value) { this.dbXREF = value; }
}
