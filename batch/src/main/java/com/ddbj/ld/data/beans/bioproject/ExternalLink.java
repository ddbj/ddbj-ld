package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> 取り込み、修正

public class ExternalLink {
    private String label;
    private String category;
    private String url;
<<<<<<< HEAD
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
=======
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
>>>>>>> 取り込み、修正
}
