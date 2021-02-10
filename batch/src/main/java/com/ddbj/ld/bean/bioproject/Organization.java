package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Organization {
    private String type;
    private String role;
    private String orgID;
    private String url;
    private OrganizationName name;
    private Contact contact;

    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getType() { return type; }
    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setType(String value) { this.type = value; }

    @JsonProperty("role")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRole() { return role; }
    @JsonProperty("role")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRole(String value) { this.role = value; }

    @JsonProperty("org_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOrgID() { return orgID; }
    @JsonProperty("org_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrgID(String value) { this.orgID = value; }

    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getURL() { return url; }
    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setURL(String value) { this.url = value; }

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OrganizationName getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(OrganizationName value) { this.name = value; }

    @JsonProperty("Contact")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Contact getContact() { return contact; }
    @JsonProperty("Contact")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContact(Contact value) { this.contact = value; }
}
