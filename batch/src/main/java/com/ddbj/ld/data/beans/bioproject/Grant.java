package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Grant {
    private String grantID;
    private String title;
    private Agency agency;
    private List<PI> pi;

    @JsonProperty("GrantId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGrantID() { return grantID; }
    @JsonProperty("GrantId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGrantID(String value) { this.grantID = value; }

    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTitle() { return title; }
    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Agency")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Agency getAgency() { return agency; }
    @JsonProperty("Agency")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAgency(Agency value) { this.agency = value; }

    @JsonProperty("PI")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<PI> getPI() { return pi; }
    @JsonProperty("PI")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPI(List<PI> value) { this.pi = value; }
}
