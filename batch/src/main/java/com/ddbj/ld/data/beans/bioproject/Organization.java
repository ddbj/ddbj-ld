package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Organization {
    private String type;
    private String role;
    private String orgID;
    private String url;
    private OrganizationName name;
    private Contact contact;

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("role")
    public String getRole() { return role; }
    @JsonProperty("role")
    public void setRole(String value) { this.role = value; }

    @JsonProperty("org_id")
    public String getOrgID() { return orgID; }
    @JsonProperty("org_id")
    public void setOrgID(String value) { this.orgID = value; }

    @JsonProperty("url")
    public String getURL() { return url; }
    @JsonProperty("url")
    public void setURL(String value) { this.url = value; }

    @JsonProperty("Name")
    public OrganizationName getName() { return name; }
    @JsonProperty("Name")
    public void setName(OrganizationName value) { this.name = value; }

    // FIXME パブリックなXMLなので消す
    @JsonProperty("Contact")
    public Contact getContact() { return contact; }
    @JsonProperty("Contact")
    public void setContact(Contact value) { this.contact = value; }
}
