package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Attribute {
    @XmlAttribute(name = "dictionary_name")
    @JsonProperty("dictionary_name")
    private String dictionaryName;

    @XmlAttribute(name = "attribute_name")
    @JsonProperty("attribute_name")
    private String attributeName;

    @XmlAttribute(name = "display_name")
    @JsonProperty("display_name")
    private String displayName;    // 表示用の属性名. 必須.

    @XmlAttribute(name = "harmonized_name")
    @JsonProperty("harmonized_name")
    private String harmonizedName; // 名寄せした属性名. 必須.

    @XmlAttribute(name = "unit")
    @JsonProperty("unit")
    private String unit;

    @XmlValue
    @JsonProperty("content")
    private String content;        // 属性値. 必須.
}
