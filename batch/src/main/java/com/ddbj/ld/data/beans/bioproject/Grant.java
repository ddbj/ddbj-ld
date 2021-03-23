package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Grant {
    private String grantID;
    private String title;
    private Agency agency;

    @JsonProperty("GrantId")
    public String getGrantID() { return grantID; }
    @JsonProperty("GrantId")
    public void setGrantID(String value) { this.grantID = value; }

    @JsonProperty("Title")
    public String getTitle() { return title; }
    @JsonProperty("Title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Agency")
    public Agency getAgency() { return agency; }
    @JsonProperty("Agency")
    public void setAgency(Agency value) { this.agency = value; }
}
