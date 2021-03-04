package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class UserTerm {
    private String term;
    private String category;
    private String units;
    private String content;

    @JsonProperty("term")
<<<<<<< HEAD
    public String getTerm() { return term; }
    @JsonProperty("term")
    public void setTerm(String value) { this.term = value; }

    @JsonProperty("category")
    public String getCategory() { return category; }
    @JsonProperty("category")
    public void setCategory(String value) { this.category = value; }

    @JsonProperty("units")
    public String getUnits() { return units; }
    @JsonProperty("units")
    public void setUnits(String value) { this.units = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTerm() { return term; }
    @JsonProperty("term")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTerm(String value) { this.term = value; }

    @JsonProperty("category")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCategory() { return category; }
    @JsonProperty("category")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCategory(String value) { this.category = value; }

    @JsonProperty("units")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUnits() { return units; }
    @JsonProperty("units")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUnits(String value) { this.units = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setContent(String value) { this.content = value; }
}
