package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.dra.common.ID;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@JsonDeserialize(using = NameElement.NameElementDeserializer.class)
public class NameElement {
    private String abbreviation;
    private String url;
    private String content;

    @JsonProperty("abbreviation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAbbreviation() { return abbreviation; }
    @JsonProperty("abbreviation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAbbreviation(String value) { this.abbreviation = value; }

    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getURL() { return url; }
    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setURL(String value) { this.url = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    static class NameElementDeserializer extends JsonDeserializer<NameElement> {
        @Override
        public NameElement deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            NameElement value = new NameElement();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var abbreviation = null == map.get("abbreviation") ? null : map.get("abbreviation").toString();
                    var url  = null == map.get("url") ? null : map.get("url").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setAbbreviation(abbreviation);
                    value.setURL(url);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize NameElement.NameElementDeserializer");
            }
            return value;
        }
    }
}
