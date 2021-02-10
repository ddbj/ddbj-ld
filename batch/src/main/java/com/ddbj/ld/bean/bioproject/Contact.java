package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Contact {
    private String email;
    private String phone;
    private String fax;
    private Address address;
    private ContactName name;

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEmail() { return email; }
    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEmail(String value) { this.email = value; }

    @JsonProperty("phone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPhone() { return phone; }
    @JsonProperty("phone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPhone(String value) { this.phone = value; }

    @JsonProperty("fax")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFax() { return fax; }
    @JsonProperty("fax")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFax(String value) { this.fax = value; }

    @JsonProperty("Address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Address getAddress() { return address; }
    @JsonProperty("Address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAddress(Address value) { this.address = value; }

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ContactName getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(ContactName value) { this.name = value; }
}
