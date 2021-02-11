package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = OrganizationName.Deserializer.class)
@Slf4j
public class OrganizationName {
    private String abbr;
    private String content;

    @JsonProperty("abbr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAbbr() { return abbr; }
    @JsonProperty("abbr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAbbr(String value) { this.abbr = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    static class Deserializer extends JsonDeserializer<OrganizationName> {
        @Override
        public OrganizationName deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
            OrganizationName value = new OrganizationName();

            try {
                switch (jsonParser.currentToken()) {
                    case VALUE_NULL:
                        break;
                    case VALUE_STRING:
                        value.setContent(jsonParser.readValueAs(String.class));

                        break;
                    case START_OBJECT:
                        Map<String, String> map = jsonParser.readValueAs(LinkedHashMap.class);

                        value.setAbbr(map.get("abbr"));
                        value.setContent(map.get("content"));

                        break;
                    default:
                        log.error("Cannot deserialize LocusTagPrefix");
                }
                return value;
            } catch (IOException e) {
                log.error("Cannot parse LocusTagPrefix");

                return null;
            }
        }
    }
}
