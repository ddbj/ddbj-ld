package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Grant {
    private String grantID;
    private String title;
    private Agency agency;

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
}
