package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Type {
    private String location;
    private String isSingle;
    private String typeOtherDescr;
    private String locationOtherDescr;
    private String content;

    @JsonProperty("location")
    public String getLocation() { return location; }
    @JsonProperty("location")
    public void setLocation(String value) { this.location = value; }

    @JsonProperty("isSingle")
    public String getIsSingle() { return isSingle; }
    @JsonProperty("isSingle")
    public void setIsSingle(String value) { this.isSingle = value; }

    @JsonProperty("typeOtherDescr")
    public String getTypeOtherDescr() { return typeOtherDescr; }
    @JsonProperty("typeOtherDescr")
    public void setTypeOtherDescr(String value) { this.typeOtherDescr = value; }

    @JsonProperty("locationOtherDescr")
    public String getLocationOtherDescr() { return locationOtherDescr; }
    @JsonProperty("locationOtherDescr")
    public void setLocationOtherDescr(String value) { this.locationOtherDescr = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
