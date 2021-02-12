package com.ddbj.ld.bean.biosample;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Owner {
    private List<NameElement> name;
    private Contacts contacts;

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<NameElement> getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(List<NameElement> value) { this.name = value; }

    @JsonProperty("Contacts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Contacts getContacts() { return contacts; }
    @JsonProperty("Contacts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContacts(Contacts value) { this.contacts = value; }
}