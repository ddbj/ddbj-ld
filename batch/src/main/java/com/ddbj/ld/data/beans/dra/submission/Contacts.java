package com.ddbj.ld.data.beans.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class Contacts {
    private Contact contact;

    @JsonProperty("CONTACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Contact getContact() { return contact; }
    @JsonProperty("CONTACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContact(Contact value) { this.contact = value; }
}
