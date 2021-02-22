package com.ddbj.ld.data.beans.jga.dac;

import com.fasterxml.jackson.annotation.*;

public class Contact {
    private String name;
    private String email;
    private String telephoneNumber;
    private String organisation;

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() { return name; }
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(String value) { this.name = value; }

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEmail() { return email; }
    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEmail(String value) { this.email = value; }

    @JsonProperty("telephone_number")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTelephoneNumber() { return telephoneNumber; }
    @JsonProperty("telephone_number")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTelephoneNumber(String value) { this.telephoneNumber = value; }

    @JsonProperty("organisation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOrganisation() { return organisation; }
    @JsonProperty("organisation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganisation(String value) { this.organisation = value; }
}
