package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {
    private String postalCode;
    private String street;
    private String city;
    private String sub;
    private String country;

    @JsonProperty("postal_code")
    public String getPostalCode() { return postalCode; }
    @JsonProperty("postal_code")
    public void setPostalCode(String value) { this.postalCode = value; }

    @JsonProperty("Street")
    public String getStreet() { return street; }
    @JsonProperty("Street")
    public void setStreet(String value) { this.street = value; }

    @JsonProperty("City")
    public String getCity() { return city; }
    @JsonProperty("City")
    public void setCity(String value) { this.city = value; }

    @JsonProperty("Sub")
    public String getSub() { return sub; }
    @JsonProperty("Sub")
    public void setSub(String value) { this.sub = value; }

    @JsonProperty("Country")
    public String getCountry() { return country; }
    @JsonProperty("Country")
    public void setCountry(String value) { this.country = value; }
}
