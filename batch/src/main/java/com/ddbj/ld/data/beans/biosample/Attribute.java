package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attribute {
    private Reference id;
    private String dictionaryname;
    private String attributename;
    private String unit;

    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Reference getContent() { return id; }
    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(Reference value) { this.id = value; }

    @JsonProperty("dictionary_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDictionaryName() { return dictionaryname; }
    @JsonProperty("dictionary_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDictionaryName(String value) { this.dictionaryname = value; }

    @JsonProperty("attribute_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAttributeName() { return attributename; }
    @JsonProperty("attribute_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAttributeName(String value) { this.attributename = value; }

    @JsonProperty("unit")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUnit() { return unit; }
    @JsonProperty("unit")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUnit(String value) { this.unit = value; }

}
