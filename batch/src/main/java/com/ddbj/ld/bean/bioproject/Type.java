package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTypeOtherDescr() { return typeOtherDescr; }
    @JsonProperty("typeOtherDescr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTypeOtherDescr(String value) { this.typeOtherDescr = value; }

    @JsonProperty("locationOtherDescr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocationOtherDescr() { return locationOtherDescr; }
    @JsonProperty("locationOtherDescr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocationOtherDescr(String value) { this.locationOtherDescr = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
