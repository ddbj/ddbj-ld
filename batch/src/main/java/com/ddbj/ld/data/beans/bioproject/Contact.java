package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact {
    private String email;
    private String phone;
    private String fax;
    private Address address;
    private ContactName name;

    @JsonProperty("email")
    public String getEmail() { return email; }
    @JsonProperty("email")
    public void setEmail(String value) { this.email = value; }

    @JsonProperty("phone")
    public String getPhone() { return phone; }
    @JsonProperty("phone")
    public void setPhone(String value) { this.phone = value; }

    @JsonProperty("fax")
    public String getFax() { return fax; }
    @JsonProperty("fax")
    public void setFax(String value) { this.fax = value; }

    @JsonProperty("Address")
    public Address getAddress() { return address; }
    @JsonProperty("Address")
    public void setAddress(Address value) { this.address = value; }

    @JsonProperty("Name")
    public ContactName getName() { return name; }
    @JsonProperty("Name")
    public void setName(ContactName value) { this.name = value; }
}
