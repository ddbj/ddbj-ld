package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class ExternalLink {
    private String label;
    private String category;
    private String url;
    private List<DBXREF> dbXREF;

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabel() { return label; }
    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("category")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCategory() { return category; }
    @JsonProperty("category")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCategory(String value) { this.category = value; }

    @JsonProperty("URL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getURL() { return url; }
    @JsonProperty("URL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setURL(String value) { this.url = value; }

    @JsonProperty("dbXREF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<DBXREF> getDBXREF() { return dbXREF; }
    @JsonProperty("dbXREF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDBXREF(List<DBXREF> value) { this.dbXREF = value; }
}
