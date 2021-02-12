package com.ddbj.ld.bean.biosample;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Contacts {
    private List<Contact> contact;

    @JsonProperty("Contact")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Contact> getContact() { return contact; }
    @JsonProperty("Contact")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContact(List<Contact> value) { this.contact = value; }
}