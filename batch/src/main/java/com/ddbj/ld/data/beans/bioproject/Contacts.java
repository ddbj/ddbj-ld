package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contacts {
    private Contact contact;

    @JsonProperty("CONTACT")
    public Contact getContact() { return contact; }
    @JsonProperty("CONTACT")
    public void setContact(Contact value) { this.contact = value; }
}
