package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Type {
    private String location;
    private String isSingle;
    private String typeOtherDescr;
    private String locationOtherDescr;
    private String content;

    @JsonProperty("location")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocation() { return location; }
    @JsonProperty("location")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocation(String value) { this.location = value; }

    @JsonProperty("isSingle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsSingle() { return isSingle; }
    @JsonProperty("isSingle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
