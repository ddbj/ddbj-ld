package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Address {
    private String postalCode;
    private String street;
    private String city;
    private String sub;
    private String country;

    @JsonProperty("postal_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPostalCode() { return postalCode; }
    @JsonProperty("postal_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPostalCode(String value) { this.postalCode = value; }

    @JsonProperty("Street")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStreet() { return street; }
    @JsonProperty("Street")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStreet(String value) { this.street = value; }

    @JsonProperty("City")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCity() { return city; }
    @JsonProperty("City")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCity(String value) { this.city = value; }

    @JsonProperty("Sub")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSub() { return sub; }
    @JsonProperty("Sub")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSub(String value) { this.sub = value; }

    @JsonProperty("Country")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCountry() { return country; }
    @JsonProperty("Country")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCountry(String value) { this.country = value; }
}
