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
public class PackageClass {
    private String displayName;
    private String content;

    @JsonProperty("display_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = PackageClass.StringDeserializer.class)
    public String getDisplayName() { return displayName; }
    @JsonProperty("display_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = PackageClass.StringDeserializer.class)
    public void setDisplayName(String value) { this.displayName = value; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = PackageClass.StringDeserializer.class)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = PackageClass.StringDeserializer.class)
    public void setContent(String value) { this.content = value; }

    static class StringDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = new String();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = jsonParser.readValueAs(Integer.class).toString();
                    break;
                case VALUE_STRING:
                    value = jsonParser.readValueAs(String.class);
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize PackageClass.StringDeserializer");
            }
            return value;
        }
    }
}
