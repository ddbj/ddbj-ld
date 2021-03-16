package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = PrimaryID.Deserializer.class)
@Slf4j
public class PrimaryID {
    private String label;
    private String content;

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabel() { return label; }
    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    static class Deserializer extends JsonDeserializer<PrimaryID> {
        @Override
        public PrimaryID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            PrimaryID value = new PrimaryID();

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

                    var label = null == map.get("label") ? null : map.get("label").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setLabel(label);
                    value.setContent(content);

                    break;
                default:
                    log.error("Cannot deserialize PrimaryID");
            }
            return value;
        }
    }
}
