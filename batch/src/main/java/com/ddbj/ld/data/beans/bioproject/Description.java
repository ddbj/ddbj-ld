package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Description {
    private String list;
    private Submitter submitter;
    private Organization organization;
    private Hold hold;
    private String access;

    @JsonProperty("List")
    public String getList() { return list; }
    @JsonProperty("List")
    public void setList(String value) { this.list = value; }

    @JsonProperty("Submitter")
    public Submitter getSubmitter() { return submitter; }
    @JsonProperty("Submitter")
    public void setSubmitter(Submitter value) { this.submitter = value; }

    @JsonProperty("Organization")
    public Organization getOrganization() { return organization; }
    @JsonProperty("Organization")
    public void setOrganization(Organization value) { this.organization = value; }

    @JsonProperty("Hold")
    public Hold getHold() { return hold; }
    @JsonProperty("Hold")
    public void setHold(Hold value) { this.hold = value; }

    @JsonProperty("Access")
    public String getAccess() { return access; }
    @JsonProperty("Access")
    public void setAccess(String value) { this.access = value; }
}
