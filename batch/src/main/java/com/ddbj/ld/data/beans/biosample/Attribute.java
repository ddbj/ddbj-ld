package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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
    @JsonDeserialize(using = Attribute.StringDeserializer.class)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Attribute.StringDeserializer.class)
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

    static class StringDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = new String();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    // 1e26といった大きな数値が入ってくるためdoubleとした
                    value = jsonParser.readValueAs(Double.class).toString();
                    break;
                case VALUE_TRUE:
                case VALUE_FALSE:
                    value = jsonParser.readValueAs(Boolean.class).toString();
                    break;
                case VALUE_STRING:
                    value = jsonParser.readValueAs(String.class);
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Attribute.StringDeserializer");
            }
            return value;
        }
    }
}
