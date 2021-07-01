package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Attribute {
    private Reference id;
    private String dictionaryname;
    private String attributename;
    private String unit;
    private String content;        // 属性値. 必須.
    private String displayname;    // 表示用の属性名. 必須.
    private String harmonizedname; // 名寄せした属性名. 必須.

    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Reference getId() { return id; }
    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setId(Reference value) { this.id = value; }

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

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    @JsonProperty("display_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDisplayName() { return displayname; }
    @JsonProperty("display_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDisplayName(String value) { this.displayname = value; }

    @JsonProperty("harmonized_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getHarmonizedName() { return harmonizedname; }
    @JsonProperty("harmonized_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setHarmonizedName(String value) { this.harmonizedname = value; }
}
