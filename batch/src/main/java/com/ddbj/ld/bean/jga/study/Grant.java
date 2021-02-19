package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Grant {
    private String grantID;
    private String title;
    private Agency agency;

    @JsonProperty("grant_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGrantID() { return grantID; }
    @JsonProperty("grant_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGrantID(String value) { this.grantID = value; }

    @JsonProperty("TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTitle() { return title; }
    @JsonProperty("TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("AGENCY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Agency getAgency() { return agency; }
    @JsonProperty("AGENCY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAgency(Agency value) { this.agency = value; }
}
