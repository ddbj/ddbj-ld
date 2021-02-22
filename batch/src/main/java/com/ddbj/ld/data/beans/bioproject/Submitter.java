package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Submitter {
    private String accountID;
    private String userName;
    private String authority;
    private Contact contact;

    @JsonProperty("account_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccountID() { return accountID; }
    @JsonProperty("account_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccountID(String value) { this.accountID = value; }

    @JsonProperty("user_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUserName() { return userName; }
    @JsonProperty("user_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUserName(String value) { this.userName = value; }

    @JsonProperty("authority")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAuthority() { return authority; }
    @JsonProperty("authority")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAuthority(String value) { this.authority = value; }

    @JsonProperty("Contact")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Contact getContact() { return contact; }
    @JsonProperty("Contact")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContact(Contact value) { this.contact = value; }
}
