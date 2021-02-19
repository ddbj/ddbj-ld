package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

public class Description {
    private String list;
    private Submitter submitter;
    private List<Organization> organization;
    private Hold hold;
    private String access;

    @JsonProperty("List")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getList() { return list; }
    @JsonProperty("List")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setList(String value) { this.list = value; }

    @JsonProperty("Submitter")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Submitter getSubmitter() { return submitter; }
    @JsonProperty("Submitter")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmitter(Submitter value) { this.submitter = value; }

    @JsonProperty("Organization")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Organization> getOrganization() { return organization; }
    @JsonProperty("Organization")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganization(List<Organization> value) { this.organization = value; }

    @JsonProperty("Hold")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Hold getHold() { return hold; }
    @JsonProperty("Hold")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setHold(Hold value) { this.hold = value; }

    @JsonProperty("Access")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccess() { return access; }
    @JsonProperty("Access")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccess(String value) { this.access = value; }
}
